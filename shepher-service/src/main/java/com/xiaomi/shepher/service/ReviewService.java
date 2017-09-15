/*
 * Copyright 2017 Xiaomi, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.shepher.service;

import com.xiaomi.shepher.biz.NodeBiz;
import com.xiaomi.shepher.biz.ReviewBiz;
import com.xiaomi.shepher.biz.SnapshotBiz;
import com.xiaomi.shepher.biz.TeamBiz;
import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.MailSenderFactory;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.dao.NodeDAO;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.ReviewRequest;
import com.xiaomi.shepher.model.Snapshot;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.util.ReviewUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class ReviewService {

    private static Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewBiz reviewBiz;

    @Autowired
    private TeamBiz teamBiz;

    @Autowired
    private MailSenderFactory mailSenderFactory;

    @Autowired
    private SnapshotBiz snapshotBiz;

    @Autowired
    private NodeBiz nodeBiz;

    @Autowired
    private NodeDAO nodeDAO;

    @Value("${server.url}")
    private String serverUrl;

    @Transactional
    public long create(String cluster, String path, String data, User creator, Action action, ReviewStatus reviewStatus)
            throws ShepherException {
        if (creator == null) {
            throw ShepherException.createIllegalParameterException();
        }
        Stat stat = nodeDAO.getStat(cluster, path, true);
        long snapshotId = snapshotBiz.getOriginalId(path, cluster, ReviewUtil.DEFAULT_CREATOR, stat, action, true);
        long newSnapshotId = snapshotBiz.create(cluster, path, data, creator.getName(), action, ReviewUtil.DEFAULT_MTIME,
                ReviewStatus.NEW, stat.getVersion() + 1, ReviewUtil.DEFAULT_REVIEWER).getId();
        Set<String> masters = teamBiz.listUserNamesByPathAndUser(creator.getId(), cluster, path, Role.MASTER);
        String reviewers = this.asStringReviewers(masters);
        long reviewId = reviewBiz.create(cluster, path, snapshotId, newSnapshotId,
                reviewStatus, creator.getName(), ReviewUtil.DEFAULT_REVIEWER, action).getId();
        logger.info("Create review request, reviewId={}, creator={}, reviewers={}", reviewId, creator, reviewers);
        mailSenderFactory.getMailSender().noticeUpdate(masters, creator.getName(), path, cluster, serverUrl + "/reviews/" + reviewId);
        return reviewId;
    }

    @Transactional
    public int update(ReviewStatus reviewStatus, long id, String reviewer, long snapshotId, long zkMtime) throws ShepherException {
        snapshotBiz.update(snapshotId, reviewStatus, reviewer, zkMtime);
        return reviewBiz.update(id, reviewStatus, reviewer);
    }

    @Transactional
    public int accept(long id, String reviewer, ReviewRequest reviewRequest) throws ShepherException {
        if (reviewRequest == null) {
            throw ShepherException.createIllegalParameterException();
        }
        if (!teamBiz.isAboveRole(reviewRequest.getCluster(), reviewRequest.getPath(), Role.MASTER, reviewer)) {
            throw ShepherException.createNoAuthorizationException();
        }
        switch (Action.get(reviewRequest.getAction())) {
            case DELETE:
                nodeBiz.delete(reviewRequest.getCluster(), reviewRequest.getPath());
                break;
            case ADD:
                logger.error("Add action doesn't need review, id:{}", id);
                break;
            case UPDATE:
                nodeBiz.update(reviewRequest.getCluster(), reviewRequest.getPath(),
                        reviewRequest.getNewSnapshotContent(), reviewRequest.getNewSnapshot());
                break;
            default:
                logger.error("Action value not corrected, id:{}", id);
                break;
        }
        Stat stat = nodeDAO.getStat(reviewRequest.getCluster(), reviewRequest.getPath(), true);
        logger.info("Accepted review request, reviewId={}, reviewer={}", id, reviewer);
        mailSenderFactory.getMailSender().noticeReview(reviewRequest.getCreator(), reviewer, reviewRequest.getPath(),
                reviewRequest.getCluster(), serverUrl + "/reviews/" + id, ReviewStatus.ACCEPTED.getDescription());
        return this.update(ReviewStatus.ACCEPTED, id, reviewer, reviewRequest.getNewSnapshot(),
                stat == null ? ReviewUtil.DEFAULT_MTIME : stat.getMtime());
    }

    @Transactional
    public int refuse(long id, String reviewer, ReviewRequest reviewRequest) throws ShepherException {
        if (reviewRequest == null) {
            throw ShepherException.createIllegalParameterException();
        }
        if (!teamBiz.isAboveRole(reviewRequest.getCluster(), reviewRequest.getPath(), Role.MASTER, reviewer)) {
            throw ShepherException.createNoAuthorizationException();
        }
        logger.info("Rejected review request, reviewId={}, reviewer={}", id, reviewer);
        mailSenderFactory.getMailSender().noticeReview(reviewRequest.getCreator(), reviewer, reviewRequest.getPath(),
                reviewRequest.getCluster(), serverUrl + "/reviews/" + id, ReviewStatus.REJECTED.getDescription());
        return this.update(ReviewStatus.REJECTED, id, reviewer, reviewRequest.getNewSnapshot(), ReviewUtil.DEFAULT_MTIME);
    }

    public ReviewRequest get(long id) {
        return reviewBiz.get(id);
    }

    @Transactional
    public void rejectIfExpired(ReviewRequest reviewRequest) throws ShepherException {
        if (reviewRequest == null) {
            throw ShepherException.createIllegalParameterException();
        }
        Snapshot snapshot = snapshotBiz.get(reviewRequest.getNewSnapshot());
        if (snapshot != null && reviewRequest.getReviewStatus() == ReviewStatus.NEW.getValue()) {
            Stat stat = nodeDAO.getStat(snapshot.getCluster(), snapshot.getPath(), true);
            if (stat == null || stat.getVersion() >= snapshot.getZkVersion()) {
                this.update(ReviewStatus.REJECTED, reviewRequest.getId(), ReviewUtil.DEFAULT_CREATOR,
                        reviewRequest.getNewSnapshot(), stat == null ? ReviewUtil.DEFAULT_MTIME : stat.getMtime());
                reviewRequest.setReviewStatus(ReviewStatus.REJECTED.getValue());
            }
        }
    }

    public int delete(long id) throws ShepherException {
        return reviewBiz.delete(id);
    }

    private String asStringReviewers(Set<String> reviewers) {
        StringBuilder sb = new StringBuilder();
        for (String reviewer : reviewers) {
            sb.append(reviewer).append(",");
        }
        if (StringUtils.isNotEmpty(sb)) {
            return sb.substring(0, sb.length() - 1);
        } else {
            return sb.toString();
        }
    }

}
