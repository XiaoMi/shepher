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
import com.xiaomi.shepher.model.Permission;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by banchuanyu on 16-11-3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PermissionMapperTest extends BaseTest {

    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void testGet() {
        String cluster = "local_test";
        String path = "/test";
        String noPath = "/no/such/pah";

        Permission permission = permissionMapper.get(cluster, path);
        Assert.assertEquals(3, permission.getId());

        Permission noPermission = permissionMapper.get(cluster, noPath);
        Assert.assertNull(noPermission);
    }

    @Test
    public void testCreate() {
        String cluster = "local_test";
        String path = "/test/sub3";
        Permission permission = new Permission(cluster, path);

        Assert.assertEquals(1, permissionMapper.create(permission));
    }

}
