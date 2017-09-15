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

package com.xiaomi.shepher.dao;

import com.xiaomi.shepher.BaseTest;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.model.PermissionTeam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by weichuyang on 16-8-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionTeamMapperTest extends BaseTest {
    @Autowired
    private PermissionTeamMapper permissionTeamMapper;

    @Test
    public void testGetByStatus() {
        int status = Status.AGREE.getValue();
        int expectedSize = 1;
        List<PermissionTeam> permissionTeams = permissionTeamMapper.list(status);
        Assert.assertEquals(expectedSize, permissionTeams.size());
    }

    @Test
    public void testGetByClusterAndSinglePath() {
        int status = Status.AGREE.getValue();
        String cluster = "local_test";
        String path = "/test/sub1";
        int expectedSize = 1;

        List<PermissionTeam> permissionTeams = permissionTeamMapper.listByPath(cluster, path, status);
        Assert.assertEquals(expectedSize, permissionTeams.size());
    }

    @Test
    public void testGetByClusterAndMultiPaths() {
        int status = Status.AGREE.getValue();
        String cluster = "local_test";
        List<String> paths = new ArrayList<>(2);
        paths.add("/test/sub1");
        paths.add("/test/sub1/ss1");
        int expectedSize = 1;

        List<PermissionTeam> permissionTeams = permissionTeamMapper.listByPaths(cluster, paths, status);
        Assert.assertEquals(expectedSize, permissionTeams.size());
    }

    @Test
    public void testGetByTeamIdAndNode() {
        int status = Status.AGREE.getValue();
        int teamId = 5;
        String cluster = "local_test";
        String path = "/test/sub1";
        int expectedId = 2;

        PermissionTeam permissionTeam = permissionTeamMapper.get(teamId, cluster, path, status);
        Assert.assertEquals(expectedId, permissionTeam.getId());
    }

    @Test
    public void testGetByTeamIdAndStatus() {
        int status = Status.PENDING.getValue();
        int teamId = 5;
        int expectedSize = 1;

        List<PermissionTeam> permissionTeams = permissionTeamMapper.listByTeam(teamId, status);
        Assert.assertEquals(expectedSize, permissionTeams.size());
    }

    @Test
    public void testUpdate() {
        long id = 3;
        int status = Status.AGREE.getValue();
        int updateResult = permissionTeamMapper.update(id, status);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    @Test
    public void testCreate() {
        long permissionId = 4;
        long teamId = 5;
        int status = Status.AGREE.getValue();

        PermissionTeam permissionTeam = new PermissionTeam(permissionId, teamId, status);
        int updateResult = permissionTeamMapper.create(permissionTeam);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

}
