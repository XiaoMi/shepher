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
import com.xiaomi.shepher.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 16-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest extends BaseTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetByName() {
        User user = userMapper.getByName("banchuanyu");
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testGetById() {
        User user = userMapper.getById(1);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void testList() {
        Set<Long> teams = new HashSet<>();
        teams.add(1L);
        teams.add(5L);
        List<User> users = userMapper.list(teams, Status.PENDING.getValue(), Role.MEMBER.getValue());
        Assert.assertNotNull(users);
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void testCreate() {
        String name = "testuser1";
        User user = new User(name);
        int result = userMapper.create(user);

        Assert.assertEquals(4, user.getId());
        Assert.assertEquals(1, result);
    }

}
