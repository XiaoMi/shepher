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

import com.xiaomi.shepher.BaseTest;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.model.Team;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.model.UserTeam;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * TeamService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamServiceTest extends BaseTest {

    @Autowired
    private TeamService teamService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create(long userId, String teamName, String cluster, String path)
     */
    @Test
    public void testCreate() throws Exception {
        long userId = 2;
        String teamName = "test_team";
        String cluster = "local_test";
        String path = "/test";

        boolean createResult = teamService.create(userId, teamName, cluster, path);
        Assert.assertEquals(true, createResult);
    }

    /**
     * Method: addApply(long userId, long teamId, Role role)
     */
    @Test
    public void testAddApplyForUserIdTeamIdRole() throws Exception {
        long userId = 2;
        long teamId = 5;
        Role role = Role.MEMBER;

        UserTeam userTeam = teamService.addApply(userId, teamId, role);
        Assert.assertNotNull(userTeam);
        Assert.assertNotEquals(1, userTeam.getId());
    }

    /**
     * Method: addApply(User user, long teamId, Role role)
     */
    @Test
    public void testAddApplyForUserTeamIdRole() throws Exception {
    }

    /**
     * Method: addMember(User member, long teamId, Role role, User creator)
     */
    @Test
    public void testAddMember() throws Exception {
    }

    /**
     * Method: listUserTeamsPending(Team team)
     */
    @Test
    public void testListUserTeamsPending() throws Exception {
        long userId = 2;
        long teamId = 5;
        Role role = Role.MEMBER;
        Team team = teamService.get(teamId);

        teamService.addApply(userId, teamId, role);
        List<UserTeam> applies = teamService.listUserTeamsPending(team);
        Assert.assertNotNull(applies);
        Assert.assertEquals(1, applies.size());
    }

    /**
     * Method: listUserTeamsAgree(Team team)
     */
    @Test
    public void testListUserTeamsAgree() throws Exception {
        long teamId = 1;
        Team team = teamService.get(teamId);

        List<UserTeam> applies = teamService.listUserTeamsAgree(team);
        Assert.assertNotNull(applies);
        Assert.assertEquals(3, applies.size());
    }

    /**
     * Method: listUserTeamsJoined(User user)
     */
    @Test
    public void testListUserTeamsJoined() throws Exception {
        long userId = 3;
        User user = new User();
        user.setId(userId);

        List<UserTeam> joinedTeams = teamService.listUserTeamsJoined(user);
        Assert.assertNotNull(joinedTeams);
        Assert.assertEquals(2, joinedTeams.size());
    }

    /**
     * Method: listTeamsToJoin(long userId, String cluster, String path)
     */
    @Test
    public void testListTeamsToJoin() throws Exception {
        long userId = 4;
        String cluster = "local_test";
        String path = "/test/sub1";
        List<Team> teams = teamService.listTeamsToJoin(userId, cluster, path);
        Assert.assertNotNull(teams);
        Assert.assertEquals(1, teams.size());
    }

    /**
     * Method: listTeamsJoined(long userId, String cluster, String path)
     */
    @Test
    public void testListTeamsJoined() throws Exception {
        long userId = 3;
        String cluster = "local_test";
        String path = "/test/sub1";

        List<Team> teams = teamService.listTeamsJoined(userId, cluster, path);
        Assert.assertNotNull(teams);
        Assert.assertEquals(1, teams.size());
    }

    /**
     * Method: get(String teamName)
     */
    @Test
    public void testGetTeamName() throws Exception {
        String teamName = "admin";
        Team team = teamService.get(teamName);
        Assert.assertNotNull(team);
        Assert.assertEquals(1, team.getId());
    }

    /**
     * Method: get(long teamId)
     */
    @Test
    public void testGetTeamId() throws Exception {
        long teamId = 1;
        Team team = teamService.get(teamId);
        Assert.assertNotNull(team);
        Assert.assertEquals("admin", team.getName());
    }

    /**
     * Method: updateStatus(long id, Status status)
     */
    @Test
    public void testUpdateStatus() throws Exception {
        long id = 3;
        Status status = Status.DELETE;
        int updateResult = teamService.updateStatus(id, status);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    /**
     * Method: agreeJoin(User user, long id, long teamId)
     */
    @Test
    public void testAgreeJoin() throws Exception {
    }

    /**
     * Method: refuseJoin(User user, long id, long teamId)
     */
    @Test
    public void testRefuseJoin() throws Exception {
    }

    /**
     * Method: updateRole(long id, Role role)
     */
    @Test
    public void testUpdateRole() throws Exception {
        long id = 3;
        Role role = Role.MEMBER;
        int updateResult = teamService.updateRole(id, role);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    /**
     * Method: hasApplied(long teamId, String cluster, String path)
     */
    @Test
    public void testHasApplied() throws Exception {
        long teamId = 5;
        String cluster = "local_test";
        String path = "/test/sub1";

        boolean hasApplied = teamService.hasApplied(teamId, cluster, path);
        Assert.assertEquals(true, hasApplied);
    }

    /**
     * Method: isMaster(long userId, long teamId)
     */
    @Test
    public void testIsMaster() throws Exception {
        long userId = 1;
        long teamId = 1;
        boolean isMaster = teamService.isMaster(userId, teamId);
        Assert.assertEquals(true, isMaster);
    }

    /**
     * Method: isOwner(long userId, long teamId)
     */
    @Test
    public void testIsOwner() throws Exception {
        long userId = 1;
        long teamId = 1;
        boolean isOwner = teamService.isOwner(userId, teamId);
        Assert.assertEquals(true, isOwner);
    }

    /**
     * Method: isAdmin(long userId)
     */
    @Test
    public void testIsAdmin() throws Exception {
        long userId = 1;
        boolean isAdmin = teamService.isAdmin(userId);
        Assert.assertEquals(true, isAdmin);
    }

} 
