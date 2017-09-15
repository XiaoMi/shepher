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

package com.xiaomi.shepher.biz;

import com.xiaomi.shepher.common.DaoValidator;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.dao.PermissionTeamMapper;
import com.xiaomi.shepher.dao.TeamMapper;
import com.xiaomi.shepher.dao.UserMapper;
import com.xiaomi.shepher.dao.UserTeamMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.PermissionTeam;
import com.xiaomi.shepher.model.Team;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.model.UserTeam;
import com.xiaomi.shepher.util.ParentPathParser;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 16-8-9.
 */
@Service
public class TeamBiz {

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PermissionTeamMapper permissionTeamMapper;

    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private UserMapper userMapper;

    public Team create(String name, long owner) throws ShepherException {
        if (StringUtils.isBlank(name) || owner == 0L) {
            throw ShepherException.createIllegalParameterException();
        }
        Team team = new Team(name, owner);
        int count;
        try {
            count = teamMapper.create(team);
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return team;
    }

    public Team getById(long id) {
        return teamMapper.getById(id);
    }

    public Team getByName(String name) throws ShepherException {
        if (StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }
        return teamMapper.getByName(name);
    }

    public int delete(long id) throws ShepherException {
        int count;
        try {
            count = teamMapper.delete(id);
        } catch (Exception e) {
            throw ShepherException.createDBDeleteErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_DELETE);
        return count;
    }

    public boolean isAboveRole(String cluster, String path, Role role, Long userId) throws ShepherException {
        Set<Long> userIds = this.listUserIdsByPath(cluster, path, role);
        return userIds.contains(userId);
    }

    public boolean isAboveRole(String cluster, String path, Role role, String userName) throws ShepherException {
        Set<String> userNames = this.listUserNamesByPath(cluster, path, role);
        return userNames.contains(userName);
    }

    private List<User> listUsersByTeams(Set<Long> teams, Status status, Role role) throws ShepherException {
        if (teams == null || status == null || role == null) {
            throw ShepherException.createIllegalParameterException();
        }
        if (teams.isEmpty()) {
            return Collections.emptyList();
        }
        return userMapper.list(teams, status.getValue(), role.getValue());
    }

    public Set<String> listUserNamesByPathAndUser(long userId, String cluster, String path, Role role) throws ShepherException {
        Set<String> userNames = new HashSet<>();
        Set<Long> teamIds = this.listTeamIdsByPathAndUser(userId, cluster, path);
        List<User> users = this.listUsersByTeams(teamIds, Status.AGREE, role);
        for (User user : users) {
            userNames.add(user.getName());
        }
        return userNames;
    }

    public Set<Long> listUserIdsByPath(String cluster, String path, Role role) throws ShepherException {
        Set<Long> userIds = new HashSet<>();
        Set<Long> teamIds = this.listTeamIdsByPath(cluster, path, Status.AGREE);
        List<User> users = this.listUsersByTeams(teamIds, Status.AGREE, role);
        for (User user : users) {
            userIds.add(user.getId());
        }
        return userIds;
    }

    public Set<String> listUserNamesByPath(String cluster, String path, Role role) throws ShepherException {
        Set<String> userNames = new HashSet<>();
        Set<Long> teamIds = this.listTeamIdsByPath(cluster, path, Status.AGREE);
        List<User> users = this.listUsersByTeams(teamIds, Status.AGREE, role);
        for (User user : users) {
            userNames.add(user.getName());
        }
        return userNames;
    }

    public Set<Long> listTeamIdsByPathAndUser(long user, String cluster, String path) throws ShepherException {
        if (StringUtils.isBlank(cluster) || path == null) {
            throw ShepherException.createIllegalParameterException();
        }
        List<Team> teams = this.listTeamsByPathAndUser(user, cluster, path, true);
        return this.parseTeamIdsFromTeams(teams);
    }

    public List<Team> listTeamsByPathAndUser(long userId, String cluster, String path, boolean joined) {
        List<String> parentPaths = ParentPathParser.parse(path);
        List<PermissionTeam> permissionTeams = Collections.emptyList();
        if (parentPaths != null && !parentPaths.isEmpty()) {
            permissionTeams = permissionTeamMapper.listByPaths(cluster, parentPaths, Status.AGREE.getValue());
        }
        List<UserTeam> userTeams = userTeamMapper.listByUser(userId, Status.AGREE.getValue());
        Set<Long> teamIds = this.parseTeamIdsFromUserTeams(userTeams);
        List<Team> results = new ArrayList<>();
        for (PermissionTeam permissionTeam : permissionTeams) {
            if (joined == teamIds.contains(permissionTeam.getTeamId())) {
                Team team = new Team();
                team.setId(permissionTeam.getTeamId());
                team.setName(permissionTeam.getTeamName());
                results.add(team);
            }
        }

        return results;
    }

    private Set<Long> listTeamIdsByPath(String cluster, String path, Status status) throws ShepherException {
        if (StringUtils.isBlank(cluster) || path == null || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        List<PermissionTeam> permissionTeams = this.listByPath(cluster, path, status);
        return this.parseTeamIdsFromPermissionTeams(permissionTeams);
    }

    private List<PermissionTeam> listByPath(String cluster, String path, Status status) throws ShepherException {
        if (StringUtils.isBlank(cluster) || path == null || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        List<String> parentPaths = ParentPathParser.parse(path);
        return this.listByPaths(cluster, parentPaths, status);
    }

    public List<PermissionTeam> listByPaths(String cluster, List<String> paths, Status status) throws ShepherException {
        if (StringUtils.isBlank(cluster) || paths == null || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        if (paths.isEmpty()) {
            return Collections.emptyList();
        }
        return permissionTeamMapper.listByPaths(cluster, paths, status.getValue());
    }

    private Set<Long> parseTeamIdsFromUserTeams(List<UserTeam> userTeams) {
        if (userTeams == null) {
            return Collections.emptySet();
        }
        Set<Long> teamIds = new HashSet<>();
        for (UserTeam userteam : userTeams) {
            teamIds.add(userteam.getTeamId());
        }
        return teamIds;
    }

    private Set<Long> parseTeamIdsFromPermissionTeams(List<PermissionTeam> permissionTeams) {
        if (permissionTeams == null) {
            return Collections.emptySet();
        }
        Set<Long> teamIds = new HashSet<>();
        for (PermissionTeam permissionTeam : permissionTeams) {
            teamIds.add(permissionTeam.getTeamId());
        }
        return teamIds;
    }

    private Set<Long> parseTeamIdsFromTeams(List<Team> teams) {
        if (teams == null) {
            return Collections.emptySet();
        }
        Set<Long> teamIds = new HashSet<>();
        for (Team team : teams) {
            teamIds.add(team.getId());
        }
        return teamIds;
    }

}
