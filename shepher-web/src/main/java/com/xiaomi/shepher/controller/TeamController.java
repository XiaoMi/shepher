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
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.PermissionTeam;
import com.xiaomi.shepher.model.Team;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.model.UserTeam;
import com.xiaomi.shepher.service.PermissionService;
import com.xiaomi.shepher.service.TeamService;
import com.xiaomi.shepher.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
 * The {@link TeamController} is entrance of team module,
 * mapped to freemarker in directory templates/team.
 *
 * Created by weichuyang on 16-7-8.
 */
@Controller
@RequestMapping("/teams")
@LoginRequired
public class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserHolder userHolder;

    /**
     * Index of team module, lists teams joined.
     *
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping("")
    public String index(Model model) throws ShepherException {
        User user = userHolder.getUser();
        List<UserTeam> teams = teamService.listUserTeamsJoined(user);

        model.addAttribute("user", user);
        model.addAttribute("teams", teams);

        return "/team/index";
    }

    /**
     * Manages members in the team.
     * Needs role master.
     *
     * @param teamId
     * @param model
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/manage", method = RequestMethod.GET)
    public String manage(@PathVariable("team") long teamId, Model model) throws ShepherException {
        User user = userHolder.getUser();
        Team team = teamService.get(teamId);
        List<UserTeam> members = teamService.listUserTeamsAgree(team);
        List<UserTeam> pendings = teamService.listUserTeamsPending(team);
        Role[] roles = Role.values();

        model.addAttribute("user", user);
        model.addAttribute("currentTeam", team);
        model.addAttribute("members", members);
        model.addAttribute("pendings", pendings);
        model.addAttribute("roles", roles);

        return "/team/manage";
    }

    /**
     * Lists members in the team.
     *
     * @param teamId
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/{team}/members", method = RequestMethod.GET)
    public String listMembers(@PathVariable("team") long teamId, Model model) throws ShepherException {
        User user = userHolder.getUser();
        Team team = teamService.get(teamId);
        List<UserTeam> members = teamService.listUserTeamsAgree(team);

        model.addAttribute("user", user);
        model.addAttribute("currentTeam", team);
        model.addAttribute("members", members);

        return "/team/member";
    }

    /**
     * Lists permission of the team.
     *
     * @param teamId
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/{team}/permission", method = RequestMethod.GET)
    public String permission(@PathVariable("team") long teamId, Model model) throws ShepherException {
        User user = userHolder.getUser();
        Team team = teamService.get(teamId);
        List<PermissionTeam> authorizedPermissions = permissionService.listPermissionTeamsByTeam(teamId, Status.AGREE);
        List<PermissionTeam> pendingPermissions = permissionService.listPermissionTeamsByTeam(teamId, Status.PENDING);
        boolean isMaster = teamService.isMaster(user.getId(), teamId) || teamService.isOwner(user.getId(), teamId);

        model.addAttribute("user", user);
        model.addAttribute("currentTeam", team);
        model.addAttribute("authorizedPermissions", authorizedPermissions);
        model.addAttribute("pendingPermissions", pendingPermissions);
        model.addAttribute("isMaster", isMaster);

        return "/team/permission";
    }

    /**
     * Agrees a user to join a team.
     * Needs role master.
     *
     * @param teamId
     * @param id
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/agree", method = RequestMethod.POST)
    public String agree(@PathVariable("team") long teamId, @RequestParam("id") long id) throws ShepherException {
        User user = userHolder.getUser();
        teamService.agreeJoin(user, id, teamId);
        return "redirect:/teams/" + teamId + "/manage";
    }

    /**
     * Refuses a user to join a team.
     * Needs role master.
     *
     * @param teamId
     * @param id
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/refuse", method = RequestMethod.POST)
    public String refuse(@PathVariable("team") long teamId, @RequestParam("id") long id) throws ShepherException {
        User user = userHolder.getUser();
        teamService.refuseJoin(user, id, teamId);
        return "redirect:/teams/" + teamId + "/manage";
    }

    /**
     * Deletes a user in the team.
     * Needs role master.
     *
     * @param teamId
     * @param id
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("team") long teamId, @RequestParam("id") long id) throws ShepherException {
        teamService.updateStatus(id, Status.DELETE);
        return "redirect:/teams/" + teamId + "/manage";
    }

    /**
     * Updates role of a team member.
     * Needs role master.
     *
     * @param teamId
     * @param id
     * @param roleValue
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/role", method = RequestMethod.POST)
    public String updateRole(@PathVariable("team") long teamId, @RequestParam("id") long id, @RequestParam("role") int roleValue)
            throws ShepherException {
        Role role = Role.get(roleValue);
        if (role == null) {
            throw ShepherException.createNoSuchRoleException();
        }
        teamService.updateRole(id, role);
        return "redirect:/teams/" + teamId + "/manage";
    }

    /**
     * Apply to join a team.
     *
     * @param teamId
     * @param roleValue
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public String join(@RequestParam("team") long teamId, @RequestParam("role") int roleValue) throws ShepherException {
        if (logger.isDebugEnabled()) {
            logger.debug("team = {}, role = {}", teamId, roleValue);
        }
        Role role = Role.get(roleValue);
        if (role == null) {
            throw ShepherException.createNoSuchRoleException();
        }
        User user = userHolder.getUser();
        teamService.addApply(user, teamId, role);
        return "redirect:/teams";
    }

    /**
     * Creates a team.
     *
     * @param teamName
     * @param cluster
     * @param path
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam("name") String teamName, @RequestParam("cluster") String cluster, @RequestParam("path") String path)
            throws ShepherException {
        if (StringUtils.isBlank(teamName)) {
            throw ShepherException.createIllegalParameterException();
        }
        User user = userHolder.getUser();
        teamService.create(user.getId(), teamName, cluster, path);
        return "redirect:/teams";
    }

    /**
     * Displays page of team apply.
     *
     * @param cluster
     * @param path
     * @param model
     * @return
     * @throws ShepherException
     */
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    public String apply(@RequestParam("cluster") String cluster, @RequestParam("path") String path, Model model) throws ShepherException {
        Role[] roles = Role.values();
        User user = userHolder.getUser();

        List<Team> teamsToJoin = teamService.listTeamsToJoin(user.getId(), cluster, path);
        List<Team> teamsJoined = teamService.listTeamsJoined(user.getId(), cluster, path);

        model.addAttribute("cluster", cluster);
        model.addAttribute("path", path);
        model.addAttribute("roles", roles);
        model.addAttribute("teams", teamsToJoin);
        model.addAttribute("teamsJoined", teamsJoined);

        return "/team/apply";
    }

    /**
     * Apply permission of a team.
     * Needs role master.
     *
     * @param teamId
     * @param cluster
     * @param path
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/apply", method = RequestMethod.POST)
    public String applyPermission(@PathVariable("team") long teamId, @RequestParam("cluster") String cluster, @RequestParam("path") String path)
            throws ShepherException {
        if (StringUtils.isBlank(path) || !path.startsWith("/")) {
            throw ShepherException.createIllegalParameterException();
        }

        if (!teamService.hasApplied(teamId, cluster, path)) {
            permissionService.addPending(teamId, cluster, path);
        }
        return "redirect:/teams/" + teamId + "/permission";
    }

    /**
     * Adds a user to a team.
     * Needs role master.
     *
     * @param teamId
     * @param userName
     * @param roleValue
     * @return
     * @throws ShepherException
     */
    @Auth(Jurisdiction.TEAM_MASTER)
    @RequestMapping(value = "/{team}/members/add", method = RequestMethod.POST)
    public String addMember(@PathVariable("team") long teamId, @RequestParam("user") String userName, @RequestParam("role") int roleValue)
            throws ShepherException {
        User member = userService.get(userName);
        User user = userHolder.getUser();
        Role role = Role.get(roleValue);
        if (member == null) {
            throw ShepherException.createNoSuchUserException();
        } else if (role == null) {
            throw ShepherException.createNoSuchRoleException();
        }

        teamService.addMember(member, teamId, role, user);
        return "redirect:/teams/" + teamId + "/manage";
    }

}
