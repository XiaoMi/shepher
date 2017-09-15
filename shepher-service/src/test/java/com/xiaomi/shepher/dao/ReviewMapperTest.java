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
import com.xiaomi.shepher.model.ReviewRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by banchuanyu on 16-8-6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ReviewMapperTest extends BaseTest {

    @Autowired
    private ReviewMapper reviewMapper;

    @Test
    public void testCreate() {
        ReviewRequest reviewRequest = new ReviewRequest("local_test", "/test/test2", 1, 2, 10, "banchuanyu", "reviewer", new Date(), 30);
        int result = reviewMapper.create(reviewRequest);
        Assert.assertEquals(1, result);
        Assert.assertEquals(2, reviewRequest.getId());
    }

    @Test
    public void testDelete() {
        int result = reviewMapper.delete(1);
        Assert.assertEquals(1, result);
    }

    @Test
    public void testUpdate() {
        ReviewRequest reviewRequest = new ReviewRequest("local_test", "/test/test2", 1, 2, 10, "banchuanyu", "reviewer", new Date(), 30);
        int result = reviewMapper.update(1, reviewRequest.getReviewStatus(), reviewRequest.getReviewer());
        Assert.assertEquals(1, result);
    }

    @Test
    public void testGet() {
        ReviewRequest reviewRequest = reviewMapper.get(1);
        Assert.assertEquals(1, reviewRequest.getId());
    }
}
