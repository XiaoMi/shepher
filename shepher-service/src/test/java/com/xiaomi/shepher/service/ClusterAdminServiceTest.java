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
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Cluster;
import com.xiaomi.shepher.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * ClusterAdminService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClusterAdminServiceTest extends BaseTest {

    @Autowired
    private ClusterAdminService clusterAdminService;

    @Autowired
    private UserHolder userHolder;

    @Before
    public void before() throws Exception {
        userHolder.setUser(new User("test"));
    }

    @After
    public void after() throws Exception {
        userHolder.clean();
    }

    /**
     * Method: create(String name, String config)
     */
    @Test
    public void testCreate() throws Exception {
        clusterAdminService.create("name", "config");
        List<Cluster> clusters = clusterAdminService.all();
        Assert.assertNotNull(clusters);
        Assert.assertEquals(2, clusters.size());

        thrown.expect(ShepherException.class);
        clusterAdminService.create(null, "config");
        clusterAdminService.create("local_test", "config");
    }

    /**
     * Method: update(String name, String config)
     */
    @Test
    public void testUpdate() throws Exception {
        clusterAdminService.update("local_test", "config");
        List<Cluster> clusters = clusterAdminService.all();
        Assert.assertNotNull(clusters);
        Assert.assertEquals(1, clusters.size());
        Assert.assertEquals("config", clusters.get(0).getConfig());

        thrown.expect(ShepherException.class);
        clusterAdminService.update(null, "config");
    }

    /**
     * Method: delete(String name)
     */
    @Test
    public void testDelete() throws Exception {
        clusterAdminService.delete("local_test");
        List<Cluster> clusters = clusterAdminService.all();
        Assert.assertNotNull(clusters);
        Assert.assertEquals(0, clusters.size());

        thrown.expect(ShepherException.class);
        clusterAdminService.delete(null);
    }

    /**
     * Method: all()
     */
    @Test
    public void testAll() throws Exception {
        List<Cluster> clusters = clusterAdminService.all();
        Assert.assertNotNull(clusters);
        Assert.assertEquals(1, clusters.size());
    }

} 
