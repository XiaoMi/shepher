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

package com.xiaomi.shepher.controller;

import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.LoginRequired;
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Cluster;
import com.xiaomi.shepher.model.ReviewRequest;
import com.xiaomi.shepher.model.Snapshot;
import com.xiaomi.shepher.service.PermissionService;
import com.xiaomi.shepher.service.ReviewService;
import com.xiaomi.shepher.service.SnapshotService;
import com.xiaomi.shepher.util.ClusterUtil;
import com.xiaomi.shepher.util.ParamUtils;
import com.xiaomi.shepher.util.ParentPathParser;
import com.xiaomi.shepher.util.ReviewUtil;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The {@link ReviewController} is entrance of review module,
 * mapped to freemarker in directory templates/review.
 *
 * Created by zhangpeng on 16-11-04.
 */

@Controller
@LoginRequired
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserHolder userHolder;

    /**
     * Lists snapshots of a zk node.
     * At most 100 per page.
     *
     * @param model
     * @param path
     * @param cluster
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "snapshots/clusters/{cluster}", method = RequestMethod.GET)
    public String snapshot(Model model, @RequestParam(value = "path", defaultValue = "/") String path,
                           @PathVariable("cluster") String cluster) throws ShepherException {
        List<Snapshot> snapshots = snapshotService.getByPath(path, cluster, 0, ReviewUtil.MAX_PAGINATION_LIMIT);
        ReviewUtil.generateSummary(snapshots);

        model.addAttribute("snapshots", snapshots);
        model.addAttribute("backPath", path);
        return "review/snapshots";
    }

    /**
     * Displays a snapshot.
     *
     * @param model
     * @param id
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "snapshots/{id}", method = RequestMethod.GET)
    public String snapshot(Model model, @PathVariable(value = "id") long id) throws ShepherException {
        Snapshot snapshot = snapshotService.getById(id);
        List<Cluster> clusters = ClusterUtil.getClusters();

        if (snapshot == null) {
            throw ShepherException.createNoSuchSnapshotException();
        }
        model.addAttribute("clusters", clusters);
        model.addAttribute("cluster", snapshot.getCluster());
        model.addAttribute("snapshot", snapshot);
        model.addAttribute("data", StringEscapeUtils.escapeHtml4(snapshot.getContent()));
        if (snapshot.getAction() == Action.DELETE.getValue()) {
            String parentPath = ParentPathParser.getParent(snapshot.getPath());
            model.addAttribute("backPath", parentPath);
        } else {
            model.addAttribute("backPath", snapshot.getPath());
        }
        return "review/snapshot";
    }

    /**
     * Displays a review apply.
     *
     * @param model
     * @param id
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "reviews/{id}", method = RequestMethod.GET)
    public String review(Model model, @PathVariable(value = "id") long id) throws ShepherException {
        ReviewRequest reviewRequest = reviewService.get(id);
        if (reviewRequest == null) {
            throw ShepherException.createNoSuchReviewException();
        }
        reviewService.rejectIfExpired(reviewRequest);
        model.addAttribute("clusters", ClusterUtil.getClusters());
        model.addAttribute("cluster", reviewRequest.getCluster());
        model.addAttribute("paths", reviewRequest.getPath().split("/"));
        model.addAttribute("reviewRequest", reviewRequest);
        model.addAttribute("content", StringEscapeUtils.escapeHtml4(reviewRequest.getSnapshotContent()));
        model.addAttribute("newContent", StringEscapeUtils.escapeHtml4(reviewRequest.getNewSnapshotContent()));

        String backPath = reviewRequest.getPath();
        if (reviewRequest.getAction() == Action.DELETE.getValue()) {
            backPath = ParentPathParser.getParent(backPath);
        }
        model.addAttribute("backPath", backPath);
        model.addAttribute("canReview", permissionService.isPathMaster(userHolder.getUser().getName(),
                reviewRequest.getCluster(), reviewRequest.getPath()));
        return "review/review";
    }

    /**
     * Accepts a review apply.
     *
     * @param model
     * @param id
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "reviews/{id}/accept", method = RequestMethod.POST)
    public String acceptReview(Model model, @PathVariable(value = "id") long id) throws ShepherException {
        ReviewRequest reviewRequest = reviewService.get(id);
        if (reviewRequest == null) {
            throw ShepherException.createNoSuchReviewException();
        }
        reviewService.accept(id, userHolder.getUser().getName(), reviewRequest);
        return "redirect:/clusters/" + reviewRequest.getCluster() + "/nodes?path=" + ParamUtils.encodeUrl(reviewRequest.getPath());
    }

    /**
     * Rejects a review apply.
     *
     * @param model
     * @param id
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "reviews/{id}/reject", method = RequestMethod.POST)
    public String rejectReview(Model model, @PathVariable(value = "id") long id) throws ShepherException {
        ReviewRequest reviewRequest = reviewService.get(id);
        if (reviewRequest == null) {
            throw ShepherException.createNoSuchReviewException();
        }
        reviewService.refuse(id, userHolder.getUser().getName(), reviewRequest);

        return "redirect:/clusters/" + reviewRequest.getCluster() + "/nodes?path=" + ParamUtils.encodeUrl(reviewRequest.getPath());
    }

}