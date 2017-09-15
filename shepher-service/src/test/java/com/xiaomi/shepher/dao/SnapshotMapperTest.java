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
import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.model.Snapshot;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by banchuanyu on 16-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SnapshotMapperTest extends BaseTest {

    @Autowired
    private SnapshotMapper snapshotMapper;

    @Test
    public void testCreate() {
        Snapshot snapshot = new Snapshot("local_test", "/test", "content", "creator", Action.ADD.getValue(), new Date(), Status.AGREE.getValue(), 1, "reviewer");
        int result = snapshotMapper.create(snapshot);
        Assert.assertEquals(1, result);
        Assert.assertEquals(3, snapshot.getId());
    }

    @Test
    public void testListByPath() {
        List<Snapshot> snapshots = snapshotMapper.listByPath("/test/test2", "local_test", 0, 10);
        Assert.assertEquals(false, snapshots.isEmpty());
        Assert.assertEquals(2, snapshots.size());
    }

    @Test
    public void testGetByPath() throws ParseException {
        Snapshot snapshot = snapshotMapper.getByPathAndZk("/test/test2", "local_test", DateUtils.parseDate("2016-09-20 02:07:59", new String[] {"yyyy-MM-dd hh:mm:ss"}), 0);
        Assert.assertNotNull(snapshot);
    }

    @Test
    public void testGet() throws ParseException {
        Snapshot snapshot = snapshotMapper.get(1);
        Assert.assertNotNull(snapshot);
    }

    @Test
    public void testUpdate() throws ParseException {
        int result = snapshotMapper.update(1, Status.AGREE.getValue(), "reviewer", new Date());
        Assert.assertEquals(1, result);
    }

    @Test
    public void testDelete() throws ParseException {
        int result = snapshotMapper.delete(1);
        Assert.assertEquals(1, result);
    }
}
