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
import com.xiaomi.shepher.model.Snapshot;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * SnapshotService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SnapshotServiceTest extends BaseTest {

    @Autowired
    private SnapshotService snapshotService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getByPath(String path, String cluster, int offset, int limit)
     */
    @Test
    public void testGetByPath() throws Exception {
        String path = "/test/test2";
        String cluster = "local_test";
        int offset = 0;
        int limit = 20;
        List<Snapshot> snapshots = snapshotService.getByPath(path, cluster, offset, limit);
        Assert.assertNotNull(snapshots);
        Assert.assertEquals(2, snapshots.size());
    }

    /**
     * Method: getById(long id)
     */
    @Test
    public void testGetById() throws Exception {
        Snapshot snapshot = snapshotService.getById(1);
        Assert.assertNotNull(snapshot);
    }

} 
