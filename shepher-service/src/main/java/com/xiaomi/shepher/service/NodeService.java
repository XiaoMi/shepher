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
import com.xiaomi.shepher.biz.SnapshotBiz;
import com.xiaomi.shepher.biz.TeamBiz;
import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.MailSenderFactory;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.dao.NodeDAO;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.util.ReviewUtil;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class NodeService {

    private static Logger logger = LoggerFactory.getLogger(NodeService.class);

    @Autowired
    private NodeDAO nodeDAO;

    @Autowired
    private NodeBiz nodeBiz;

    @Autowired
    private MailSenderFactory mailSenderFactory;

    @Autowired
    private SnapshotBiz snapshotBiz;

    @Autowired
    private TeamBiz teamBiz;

    @Autowired
    private PermissionService permissionService;

    @Value("${server.url}")
    private String serverUrl;

    public List<String> getChildren(String cluster, String path) throws ShepherException {
        List<String> children = nodeDAO.getChildren(cluster, path);
        Collections.sort(children);
        return children;
    }

    public String getData(String cluster, String path) throws ShepherException {
        return nodeDAO.getData(cluster, path);
    }

    public Stat getStat(String cluster, String path) throws ShepherException {
        return this.getStat(cluster, path, true);
    }

    public Stat getStat(String cluster, String path, boolean returnNullIfPathNotExists) throws ShepherException {
        return nodeDAO.getStat(cluster, path, returnNullIfPathNotExists);
    }

    public void create(String cluster, String path, String data, String creator) throws ShepherException {
        nodeBiz.create(cluster, path, data);
        long zkCreationTime = nodeDAO.getCreationTime(cluster, path);
        snapshotBiz.create(cluster, path, data, creator, Action.ADD, zkCreationTime, ReviewStatus.ACCEPTED,
                ReviewUtil.NEW_CREATE_VERSION, ReviewUtil.DEFAULT_REVIEWER);
        logger.info("Create node, cluster={}, path={}, operator={}", cluster, path, creator);
    }

    public void create(String cluster, String path, String data, String creator, boolean createParents) throws ShepherException {
        nodeBiz.create(cluster, path, data, createParents);
        long zkCreationTime = nodeDAO.getCreationTime(cluster, path);
        snapshotBiz.create(cluster, path, data, creator, Action.ADD, zkCreationTime, ReviewStatus.ACCEPTED,
                ReviewUtil.NEW_CREATE_VERSION, ReviewUtil.DEFAULT_REVIEWER);
        logger.info("Create node, cluster={}, path={}, operator={}", cluster, path, creator);
    }

    public void createEphemeral(String cluster, String path, String data, String creator) throws ShepherException {
        nodeBiz.createEphemeral(cluster, path, data);
        long zkCreationTime = nodeDAO.getCreationTime(cluster, path);
        snapshotBiz.create(cluster, path, data, creator, Action.ADD, zkCreationTime, ReviewStatus.ACCEPTED,
                ReviewUtil.NEW_CREATE_VERSION, ReviewUtil.DEFAULT_REVIEWER);
        logger.info("Create ephemeral node, cluster={}, path={}, operator={}", cluster, path, creator);
    }

    public void updateWithPermission(String cluster, String path, String data, String creator) throws ShepherException {
        if (!permissionService.isPathMaster(creator, cluster, path)) {
            throw ShepherException.createNoAuthorizationException();
        }
        update(cluster, path, data, creator);
    }

    public void update(String cluster, String path, String data, String creator) throws ShepherException {
        nodeBiz.update(cluster, path, data);
        Stat stat = nodeDAO.getStat(cluster, path, true);
        snapshotBiz.create(cluster, path, data, creator, Action.UPDATE, stat.getCtime(), ReviewStatus.ACCEPTED, stat.getVersion(),
                ReviewUtil.DEFAULT_REVIEWER);
        logger.info("Update node, cluster={}, path={}, operator={}", cluster, path, creator);
    }

    public void delete(String cluster, String path, String creator) throws ShepherException {
        nodeBiz.delete(cluster, path);
        long snapshotId = snapshotBiz.create(cluster, path, ReviewUtil.EMPTY_CONTENT, creator, Action.DELETE, ReviewUtil.DEFAULT_MTIME,
                ReviewStatus.DELETED, ReviewUtil.NEW_CREATE_VERSION, ReviewUtil.DEFAULT_REVIEWER).getId();
        Set<String> masters = teamBiz.listUserNamesByPath(cluster, path, Role.MASTER);
        mailSenderFactory.getMailSender().noticeDelete(masters, creator, path, cluster, serverUrl + "/snapshots/" + snapshotId);
        logger.info("Delete node, cluster={}, path={}, operator={}", cluster, path, creator);
    }
}
