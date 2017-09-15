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

import com.xiaomi.shepher.common.Auth;
import com.xiaomi.shepher.common.Jurisdiction;
import com.xiaomi.shepher.common.LoginRequired;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.service.ClusterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The {@link ClusterAdminController} is entrance of clusters management,
 * mapped to freemarker in directory templates/admin.
 *
 * Created by zhangpeng on 16-11-03.
 */

@Controller
@LoginRequired
@Auth(Jurisdiction.SUPER_ADMIN)
@RequestMapping("/admin")
public class ClusterAdminController {

    @Autowired
    private ClusterAdminService clusterAdminService;

    /**
     * Lists clusters for admin.
     *
     * @param model
     * @return
     */
    @RequestMapping({"", "/index"})
    public String index(Model model) {
        return "admin/index";
    }

    /**
     * Creates a cluster.
     *
     * @param name
     * @param config
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("name") String name, @RequestParam("config") String config) throws ShepherException {
        clusterAdminService.create(name, config);
        return "redirect:/admin";
    }

    /**
     * Updates a cluster.
     *
     * @param name
     * @param config
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam("name") String name, @RequestParam("config") String config) throws ShepherException {
        clusterAdminService.update(name, config);
        return "redirect:/admin";
    }

    /**
     * Deletes a cluster.
     *
     * @param name
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("name") String name) throws ShepherException {
        clusterAdminService.delete(name);
        return "redirect:/admin";
    }
}