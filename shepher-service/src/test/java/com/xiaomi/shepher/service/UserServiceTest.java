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
import com.xiaomi.shepher.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * UserService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends BaseTest {

    @Autowired
    private UserService userService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: get(long id)
     */
    @Test
    public void testGetId() throws Exception {
        long id = 1;
        User user = userService.get(id);
        Assert.assertNotNull(user);
        Assert.assertEquals("banchuanyu", user.getName());
    }

    /**
     * Method: get(String name)
     */
    @Test
    public void testGetName() throws Exception {
        String name = "banchuanyu";
        User user = userService.get(name);
        Assert.assertNotNull(user);
        Assert.assertEquals(1, user.getId());
    }

    /**
     * Method: create(String name)
     */
    @Test
    public void testCreate() throws Exception {
        String name = "test_user";
        User user = userService.create(name);
        Assert.assertEquals(4, user.getId());
    }

    /**
     * Method: createIfNotExist(String name)
     */
    @Test
    public void testCreateIfNotExist() throws Exception {
        String existedName = "banchuanyu";
        User user = userService.createIfNotExist(existedName);
        Assert.assertEquals(1, user.getId());

        String name = "test_user";
        user = userService.createIfNotExist(name);
        Assert.assertNotNull(user);
        Assert.assertEquals(4, user.getId());
    }

    /**
     * Method: listByTeams(Set<Long> teams, Status status, Role role)
     */
    @Test
    public void testListByTeams() throws Exception {
        Set<Long> teams = new HashSet<>();
        teams.add(1L);
        teams.add(2L);
        List<User> users = userService.listByTeams(teams, Status.AGREE, Role.MEMBER);
        Assert.assertNotNull(users);
        Assert.assertEquals(3, users.size());
    }

    /**
     * Method: listNamesByTeams(Set<Long> teamIds, Status status, Role role)
     */
    @Test
    public void testListNamesByTeams() throws Exception {
        Set<Long> teams = new HashSet<>();
        teams.add(1L);
        teams.add(2L);
        Set<String> names = userService.listNamesByTeams(teams, Status.AGREE, Role.MEMBER);
        Assert.assertNotNull(names);
        Assert.assertEquals(3, names.size());
    }

} 
