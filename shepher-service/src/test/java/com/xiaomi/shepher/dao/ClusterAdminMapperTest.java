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
import com.xiaomi.shepher.model.Cluster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhangpeng on 16-11-3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ClusterAdminMapperTest extends BaseTest {
    @Autowired
    private ClusterAdminMapper clusterAdminMapper;

    @Test
    public void testCreate() {
        int result = clusterAdminMapper.create(new Cluster("name1", "config1"));
        Assert.assertEquals(1, result);
    }

    @Test
    public void testUpdate() {
        int result = clusterAdminMapper.update("local_test", "config2");
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGetAll() {
        List<Cluster> clusters = clusterAdminMapper.all();
        Assert.assertNotNull(clusters);
        Assert.assertEquals(1, clusters.size());
    }

    @Test
    public void testDelete() {
        int result = clusterAdminMapper.delete("local_test");
        Assert.assertEquals(1, result);
    }
}
