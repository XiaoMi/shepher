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
import com.xiaomi.shepher.common.Auth;
import com.xiaomi.shepher.common.Jurisdiction;
import com.xiaomi.shepher.common.LoginRequired;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Snapshot;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.service.NodeService;
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
 * The {@link NodeController} is entrance of node module,
 * mapped to freemarker in directory templates/cluster.
 *
 *
 * Created by zhangpeng on 16-11-04.
 */

@Controller
@LoginRequired
@RequestMapping("/clusters/{cluster}/nodes")
public class NodeController {

    private static final Logger logger = LoggerFactory.getLogger(NodeController.class);

    private static final String SLASH = "/";

    @Autowired
    private NodeService nodeService;

    @Autowired
    private SnapshotService snapshotService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserHolder userHolder;

    /**
     * Lists nodes of the cluster.
     *
     * @param path
     * @param cluster
     * @param showIp
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String home(@RequestParam(value = "path", defaultValue = SLASH) String path, @PathVariable("cluster") String cluster,
                       @RequestParam(value = "show-ip", defaultValue = "") String showIp, Model model) throws ShepherException {

        List<String> children = nodeService.getChildren(cluster, path);

        List<Snapshot> snapshots = snapshotService.getByPath(path, cluster, 0, ReviewUtil.DEFAULT_PAGINATION_LIMIT);
        ReviewUtil.generateSummary(snapshots);

        long userId = userHolder.getUser().getId();
        boolean hasPermission = permissionService.isPathMember(userId, cluster, path);
        boolean hasDeletePermission = permissionService.isPathMaster(userId, cluster, path);

        model.addAttribute("children", children);
        model.addAttribute("hasChild", !children.isEmpty());
        model.addAttribute("data", StringEscapeUtils.escapeHtml4(nodeService.getData(cluster, path)));
        model.addAttribute("stat", nodeService.getStat(cluster, path));
        model.addAttribute("path", path);
        model.addAttribute("pathArr", path.split(SLASH));
        model.addAttribute("cluster", cluster);
        model.addAttribute("clusters", ClusterUtil.getClusters());
        model.addAttribute("snapshots", snapshots);
        model.addAttribute("hasPermission", hasPermission);
        model.addAttribute("hasDeletePermission", hasDeletePermission);
        model.addAttribute("isOnebox", false);
        model.addAttribute("showIp", showIp);

        return "/cluster/index";
    }

    /**
     * Updates node of the cluster.
     *
     * @param path
     * @param cluster
     * @param data
     * @param model
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MEMBER)
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("path") String path,
                         @PathVariable("cluster") String cluster,
                         @RequestParam(value = "data") String data,
                         @RequestParam(value = "srcPath", defaultValue = "/") String srcPath,
                         @RequestParam(value = "srcCluster", defaultValue = "") String srcCluster,
                         Model model)
            throws ShepherException {
        String tempData = data;
        if (!("/".equals(srcPath)) && !("".equals(srcCluster))) {
            tempData = nodeService.getData(srcCluster, srcPath);
        }

        User user = userHolder.getUser();
        if (ClusterUtil.isPublicCluster(cluster)) {
            nodeService.update(cluster, path, tempData, user.getName());
            return "redirect:/clusters/" + cluster + "/nodes?path=" + ParamUtils.encodeUrl(path);
        }

        long reviewId = reviewService.create(cluster, path, tempData, user, Action.UPDATE, ReviewStatus.NEW);
        return "redirect:/reviews/" + reviewId;
    }

    /**
     * Deletes node of the cluster.
     *
     * @param path
     * @param cluster
     * @param model
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("path") String path, @PathVariable("cluster") String cluster, Model model) throws ShepherException {
        User user = userHolder.getUser();
        String parentPath = ParentPathParser.getParent(path);
        nodeService.delete(cluster, path, user.getName());
        return "redirect:/clusters/" + cluster + "/nodes?path=" + ParamUtils.encodeUrl(parentPath);
    }

    /**
     * Displays the page of node creation.
     *
     * @param path
     * @param cluster
     * @param showIp
     * @param model
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@RequestParam(value = "path", defaultValue = SLASH) String path, @PathVariable("cluster") String cluster,
                         @RequestParam(value = "show-ip", defaultValue = "") String showIp, Model model) throws ShepherException {
        List<String> children = nodeService.getChildren(cluster, path);

        String data = nodeService.getData(cluster, path);
        String[] pathArr = path.split(SLASH);

        model.addAttribute("children", children);
        model.addAttribute("data", data);
        model.addAttribute("path", path);
        model.addAttribute("pathArr", pathArr);
        model.addAttribute("cluster", cluster);
        model.addAttribute("clusters", ClusterUtil.getClusters());
        model.addAttribute("isOnebox", false);

        return "/cluster/create";
    }

    /**
     * Creates node of the cluster.
     *
     * @param parent
     * @param data
     * @param child
     * @param cluster
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("path") String parent,
                         @RequestParam("data") String data,
                         @RequestParam("child") String child,
                         @PathVariable("cluster") String cluster, Model model)
            throws ShepherException {
        User user = userHolder.getUser();
        String path = parent + SLASH + child;
        if (parent.equals(SLASH)) {
            path = SLASH + child;
        }
        nodeService.create(cluster, path, data, user.getName());

        return "redirect:/clusters/" + cluster + "/nodes?path=" + ParamUtils.encodeUrl(path);
    }
}
