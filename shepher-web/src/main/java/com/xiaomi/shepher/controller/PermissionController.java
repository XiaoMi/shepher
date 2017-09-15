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
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.PermissionTeam;
import com.xiaomi.shepher.model.Team;
import com.xiaomi.shepher.service.PermissionService;
import com.xiaomi.shepher.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The {@link PermissionController} is entrance of permission management,
 * mapped to freemarker in directory templates/permission.
 *
 * Created by weichuyang on 16-7-11.
 */
@Controller
@LoginRequired
@RequestMapping("permission")
@Auth(Jurisdiction.SUPER_ADMIN)
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private TeamService teamService;

    /**
     * Index of permission management.
     *
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = {"/manage", ""}, method = RequestMethod.GET)
    public String manage(Model model) throws ShepherException {
        List<PermissionTeam> permissions = permissionService.listPermissionTeamsAgree();
        List<PermissionTeam> pendings = permissionService.listPermissionTeamsPending();

        model.addAttribute("pendings", pendings);
        model.addAttribute("permissions", permissions);
        return "/permission/manage";
    }

    /**
     * Agrees to a permission apply.
     *
     * @param pid
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/agree", method = RequestMethod.POST)
    public String agree(@RequestParam("pid") long pid) throws ShepherException {
        permissionService.updateStatus(pid, Status.AGREE);
        return "redirect:/permission/manage";
    }

    /**
     * Refuses a permission apply.
     *
     * @param pid
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/refuse", method = RequestMethod.POST)
    public String refuse(@RequestParam("pid") long pid) throws ShepherException {
        permissionService.updateStatus(pid, Status.REFUSE);
        return "redirect:/permission/manage";
    }

    /**
     * Deletes a permission apply.
     *
     * @param pid
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("pid") long pid) throws ShepherException {
        permissionService.updateStatus(pid, Status.DELETE);
        return "redirect:/permission/manage";
    }

    /**
     * Adds a permission apply and auto agree to it.
     *
     * @param teamName
     * @param cluster
     * @param path
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam("team") String teamName, @RequestParam("cluster") String cluster, @RequestParam("path") String path)
            throws ShepherException {
        Team team = teamService.get(teamName);
        if (team == null) {
            throw ShepherException.createNoSuchTeamException();
        }
        permissionService.addAgree(team.getId(), cluster, path);

        return "redirect:/permission/manage";
    }

}
