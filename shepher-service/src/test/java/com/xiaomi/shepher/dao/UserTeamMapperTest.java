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
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.model.UserTeam;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by weichuyang on 16-8-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTeamMapperTest extends BaseTest {
    @Autowired
    private UserTeamMapper userTeamMapper;

    @Test
    public void testCreate() {
        long userId = 2;
        long teamId = 5;
        int role = Role.DEVELOPER.getValue();
        int status = Status.AGREE.getValue();

        int createResult = userTeamMapper.create(new UserTeam(userId, teamId, role, status));
        Assert.assertEquals(RESULT_OK, createResult);
    }

    @Test
    public void testUpdateStatus() {
        long id = 2;
        int status = Status.AGREE.getValue();
        int updateResult = userTeamMapper.updateStatus(id, status);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    @Test
    public void testUpdateRole() {
        long id = 2;
        int role = Role.DEVELOPER.getValue();
        int updateResult = userTeamMapper.updateRole(id, role);
        Assert.assertEquals(RESULT_OK, updateResult);
    }

    @Test
    public void testGetRoleValue() {
        long userId = 1;
        long teamId = 5;
        int status = Status.AGREE.getValue();

        int role = userTeamMapper.getRoleValue(userId, teamId, status);
        Assert.assertEquals(Role.MASTER.getValue(), role);
    }

    @Test
    public void testGetByTeam() {
        long teamId = 5;
        int status = Status.AGREE.getValue();

        List<UserTeam> userTeams = userTeamMapper.listByTeam(teamId, status);
        Assert.assertEquals(2, userTeams.size());
    }

    @Test
    public void testGetByUser() {
        long userId = 1;
        int status = Status.AGREE.getValue();

        List<UserTeam> userTeams = userTeamMapper.listByUser(userId, status);
        Assert.assertEquals(2, userTeams.size());
    }

    @Test
    public void testGet() {
        UserTeam userTeam = userTeamMapper.get(1);
        Assert.assertNotNull(userTeam);
        Assert.assertEquals(1, userTeam.getId());
    }

}

