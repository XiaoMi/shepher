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

import com.xiaomi.shepher.biz.PermissionBiz;
import com.xiaomi.shepher.biz.PermissionTeamBiz;
import com.xiaomi.shepher.biz.TeamBiz;
import com.xiaomi.shepher.biz.UserBiz;
import com.xiaomi.shepher.biz.UserTeamBiz;
import com.xiaomi.shepher.common.MailSenderFactory;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Team;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.model.UserTeam;
import com.xiaomi.shepher.util.ShepherConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class TeamService {

    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    private TeamBiz teamBiz;

    @Autowired
    private UserTeamBiz userTeamBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private PermissionTeamBiz permissionTeamBiz;

    @Autowired
    private PermissionBiz permissionBiz;

    @Autowired
    private MailSenderFactory mailSenderFactory;

    @Value("${server.url}")
    private String serverUrl;

    @Transactional
    public boolean create(long userId, String teamName, String cluster, String path) throws ShepherException {
        long teamId = teamBiz.create(teamName, userId).getId();
        userTeamBiz.create(userId, teamId, Role.MASTER, Status.AGREE);

        long permissionId = permissionBiz.createIfNotExists(cluster, path);
        permissionTeamBiz.create(permissionId, teamId, Status.PENDING);

        return true;
    }

    public UserTeam addApply(long userId, long teamId, Role role) throws ShepherException {
        return userTeamBiz.create(userId, teamId, role, Status.PENDING);
    }

    @Transactional
    public void addApply(User user, long teamId, Role role) throws ShepherException {
        if (user == null) {
            throw ShepherException.createIllegalParameterException();
        }
        userTeamBiz.create(user.getId(), teamId, role, Status.PENDING);
        Team team = this.get(teamId);
        if (team == null) {
            throw ShepherException.createNoSuchTeamException();
        }
        Set<Long> teamIds = new HashSet<>();
        teamIds.add(teamId);
        Set<String> masters = userBiz.listNames(teamIds, Status.AGREE, Role.MASTER);
        mailSenderFactory.getMailSender().noticeJoinTeam(masters, user.getName(), team.getName(), serverUrl + "/teams/" + teamId + "/manage");
    }

    @Transactional
    public void addMember(User member, long teamId, Role role, User creator) throws ShepherException {
        if (member == null || creator == null) {
            throw ShepherException.createIllegalParameterException();
        }

        if (userTeamBiz.listUserByTeamId(teamId).contains(member.getName())) {
            throw ShepherException.createUserExistsException();
        }

        userTeamBiz.create(member.getId(), teamId, role, Status.AGREE);
        Team team = this.get(teamId);
        if (team == null) {
            throw ShepherException.createNoSuchTeamException();
        }
        Set<String> receivers = new HashSet<>();
        receivers.add(member.getName());
        mailSenderFactory.getMailSender().noticeJoinTeamHandled(receivers, creator.getName(), Status.AGREE, team.getName(), serverUrl + "/teams/" + teamId + "/members");
    }

    public List<UserTeam> listUserTeamsPending(Team team) throws ShepherException {
        if (team == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return userTeamBiz.listByTeam(team.getId(), Status.PENDING);
    }

    public List<UserTeam> listUserTeamsAgree(Team team) throws ShepherException {
        if (team == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return userTeamBiz.listByTeam(team.getId(), Status.AGREE);
    }

    public List<UserTeam> listUserTeamsJoined(User user) throws ShepherException {
        if (user == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return userTeamBiz.listByUser(user.getId(), Status.AGREE);
    }

    public List<Team> listTeamsToJoin(long userId, String cluster, String path) {
        return teamBiz.listTeamsByPathAndUser(userId, cluster, path, false);
    }

    public List<Team> listTeamsJoined(long userId, String cluster, String path) {
        return teamBiz.listTeamsByPathAndUser(userId, cluster, path, true);
    }

    public Team get(String teamName) throws ShepherException {
        return teamBiz.getByName(teamName);
    }

    public Team get(long teamId) {
        return teamBiz.getById(teamId);
    }

    public int updateStatus(long id, Status status) throws ShepherException {
        return userTeamBiz.updateStatus(id, status);
    }

    private void noticeJoinTeamHandled(long id, User user, long teamId, Status status) throws ShepherException {
        if (user == null || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        UserTeam userTeam = userTeamBiz.get(id);
        if (userTeam == null) {
            throw ShepherException.createNoSuchUserTeamException();
        }
        Set<String> receivers = new HashSet<>();
        receivers.add(userTeam.getUserName());
        mailSenderFactory.getMailSender().noticeJoinTeamHandled(receivers, user.getName(), status, userTeam.getTeamName(), serverUrl + "/teams/" + teamId + "/members");

    }

    public int agreeJoin(User user, long id, long teamId) throws ShepherException {
        int result = this.updateStatus(id, Status.AGREE);
        this.noticeJoinTeamHandled(id, user, teamId, Status.AGREE);
        return result;
    }

    public int refuseJoin(User user, long id, long teamId) throws ShepherException {
        int result = this.updateStatus(id, Status.REFUSE);
        this.noticeJoinTeamHandled(id, user, teamId, Status.REFUSE);
        return result;
    }

    public int updateRole(long id, Role role) throws ShepherException {
        return userTeamBiz.updateRole(id, role);
    }

    public boolean hasApplied(long teamId, String cluster, String path) throws ShepherException {
        boolean accepted = permissionTeamBiz.get(teamId, cluster, path, Status.AGREE) != null;
        boolean pending = permissionTeamBiz.get(teamId, cluster, path, Status.PENDING) != null;
        return accepted || pending;
    }

    public boolean isMaster(long userId, long teamId) throws ShepherException {
        return userTeamBiz.getRoleValue(userId, teamId, Status.AGREE) == Role.MASTER.getValue();
    }

    public boolean isOwner(long userId, long teamId) {
        Team team = teamBiz.getById(teamId);
        return team != null && team.getOwner() == userId;
    }

    public boolean isAdmin(long userId) throws ShepherException {
        Team team = teamBiz.getByName(ShepherConstants.ADMIN);
        return userTeamBiz.getRoleValue(userId, team.getId(), Status.AGREE) > ShepherConstants.DEFAULT_ROLEVALUE;
    }

}
