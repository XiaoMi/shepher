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
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.model.PermissionTeam;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * PermissionService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionServiceTest extends BaseTest {

    @Autowired
    private PermissionService permissionService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: add(long permissionId, long teamId, Status status)
     */
    @Test
    public void testAddForPermissionIdTeamIdStatus() throws Exception {
        long permissionId = 3;
        long teamId = 5;
        Status status = Status.AGREE;

        PermissionTeam permissionTeam = permissionService.add(permissionId, teamId, status);
        Assert.assertNotNull(permissionTeam);
        Assert.assertEquals(4, permissionTeam.getId());
    }

    /**
     * Method: add(long teamId, String cluster, String path, Status status)
     */
    @Test
    public void testAddForTeamIdClusterPathStatus() throws Exception {
        long teamId = 5;
        Status status = Status.AGREE;
        String cluster = "local_test";
        String path = "/test";

        boolean addResult = permissionService.add(teamId, cluster, path, status);
        Assert.assertEquals(true, addResult);
    }

    /**
     * Method: addPending(long teamId, String cluster, String path)
     */
    @Test
    public void testAddPending() throws Exception {
        long teamId = 5;
        String cluster = "local_test";
        String path = "/test";

        boolean addResult = permissionService.addPending(teamId, cluster, path);
        Assert.assertEquals(true, addResult);
    }

    /**
     * Method: addAgree(long teamId, String cluster, String path)
     */
    @Test
    public void testAddAgree() throws Exception {
        long teamId = 5;
        String cluster = "local_test";
        String path = "/test";

        boolean addResult = permissionService.addAgree(teamId, cluster, path);
        Assert.assertEquals(true, addResult);
    }

    /**
     * Method: listPermissionTeamsAgree()
     */
    @Test
    public void testListPermissionTeamsAgree() throws Exception {
        List<PermissionTeam> permissionTeams = permissionService.listPermissionTeamsAgree();
        Assert.assertNotNull(permissionTeams);
        Assert.assertEquals(1, permissionTeams.size());
    }

    /**
     * Method: listPermissionTeamsByTeam(long teamId, Status status)
     */
    @Test
    public void testListPermissionTeamsByTeam() throws Exception {
        long teamId = 5;
        List<PermissionTeam> permissionTeams = permissionService.listPermissionTeamsByTeam(teamId, Status.AGREE);
        Assert.assertNotNull(permissionTeams);
        Assert.assertEquals(1, permissionTeams.size());
    }

    /**
     * Method: listPermissionTeamsPending()
     */
    @Test
    public void testListPermissionTeamsPending() throws Exception {
        List<PermissionTeam> permissionTeams = permissionService.listPermissionTeamsPending();
        Assert.assertNotNull(permissionTeams);
        Assert.assertEquals(1, permissionTeams.size());
    }

    /**
     * Method: updateStatus(long id, Status status)
     */
    @Test
    public void testUpdateStatus() throws Exception {
        int id = 3;
        Status status = Status.AGREE;
        int updateResult = permissionService.updateStatus(id, status);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    /**
     * Method: isPathMember(long userId, String cluster, String path)
     */
    @Test
    public void testIsPathMember() throws Exception {
        long userId = 3;
        String cluster = "local_test";
        String path = "/test/sub1";
        boolean isMember = permissionService.isPathMember(userId, cluster, path);
        Assert.assertEquals(true, isMember);
    }

    /**
     * Method: isPathMaster(long userId, String cluster, String path)
     */
    @Test
    public void testIsPathMasterForUserIdClusterPath() throws Exception {
        long userId = 3;
        String cluster = "local_test";
        String path = "/test/sub1";
        boolean isMaster = permissionService.isPathMaster(userId, cluster, path);
        Assert.assertEquals(true, isMaster);
    }

    /**
     * Method: isPathMaster(String userName, String cluster, String path)
     */
    @Test
    public void testIsPathMasterForUserNameClusterPath() throws Exception {
        String userName = "testuser";
        String cluster = "local_test";
        String path = "/test/sub1";
        boolean isMaster = permissionService.isPathMaster(userName, cluster, path);
        Assert.assertEquals(true, isMaster);
    }

} 
