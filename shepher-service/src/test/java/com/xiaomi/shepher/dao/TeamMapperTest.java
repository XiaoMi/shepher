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
import com.xiaomi.shepher.model.Team;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by weichuyang on 16-8-10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TeamMapperTest extends BaseTest {
    @Autowired
    private TeamMapper teamMapper;

    @Test
    public void testGetById() {
        Team team = teamMapper.getById(1);
        Assert.assertEquals("admin", team.getName());
    }

    @Test
    public void testGetByName() {
        String name = "theone";
        Team team = teamMapper.getByName(name);
        Assert.assertEquals(5, team.getId());
    }

    @Test
    public void testCreate() {
        Team team = new Team("test_team2", 1);
        long result = teamMapper.create(team);
        Assert.assertEquals(1, result);
        Assert.assertEquals(6, team.getId());
    }

    @Test
    public void testDelete() {
        Team team = new Team("test_team3", 2);
        long result = teamMapper.create(team);
        Assert.assertEquals(1, result);

        int deleteResult = teamMapper.delete(team.getId());
        Assert.assertEquals(RESULT_OK, deleteResult);
    }

}
