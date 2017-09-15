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
import com.xiaomi.shepher.biz.NodeBiz;
import com.xiaomi.shepher.biz.SnapshotBiz;
import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.MailSenderAbstract;
import com.xiaomi.shepher.common.MailSenderFactory;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.dao.NodeDAO;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.ReviewRequest;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import javax.annotation.Resource;

/**
 * ReviewService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class ReviewServiceTest extends BaseTest {

    @InjectMocks
    @Resource(name = "reviewService")
    private ReviewService reviewService;

    @Mock
    private SnapshotBiz snapshotBiz;

    @Mock
    private NodeBiz nodeBiz;

    @Mock
    private NodeDAO nodeDAO;

    @Mock
    private MailSenderFactory mailSenderFactory;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        Stat stat = new Stat(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        when(snapshotBiz.getOriginalId(anyString(), anyString(), anyString(), any(Stat.class), any(Action.class), anyBoolean()))
                .thenReturn(1L).thenThrow(ShepherException.class);
        when(nodeDAO.getStat(anyString(), anyString(), anyBoolean())).thenReturn(stat);
        when(nodeDAO.exists(anyString(), anyString())).thenReturn(true);

        when(mailSenderFactory.getMailSender()).thenReturn(new MailSenderAbstract() {
            @Override
            protected void send(String mailAddress, String title, String content) {

            }
        });
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create(String cluster, String path, String data, User creator, Action action, ReviewStatus reviewStatus)
     */
    @Test
    public void testCreate() throws Exception {

    }

    /**
     * Method: update(ReviewStatus reviewStatus, long id, String reviewer, long snapshotId, long zkMtime)
     */
    @Test
    public void testUpdate() throws Exception {
        int result = reviewService.update(ReviewStatus.ACCEPTED, 1L, "reviewer", 0L, 0L);
        Assert.assertNotEquals(0, result);
    }

    /**
     * Method: accept(long id, String reviewer, ReviewRequest reviewRequest)
     */
    @Test
    public void testAccept() throws Exception {
        int result = reviewService.accept(1L, "testuser", new ReviewRequest("local_test", "/test/sub1", 0, 0, ReviewStatus.ACCEPTED.getValue(), "testuser",
                "testuser", new Date(), Action.UPDATE.getValue()));
        Assert.assertNotEquals(0, result);
    }

    /**
     * Method: refuse(long id, String reviewer, ReviewRequest reviewRequest)
     */
    @Test
    public void testRefuse() throws Exception {
        int result = reviewService.refuse(1L, "testuser", new ReviewRequest("local_test", "/test/sub1", 0, 0, ReviewStatus.ACCEPTED.getValue(), "testuser",
                "testuser", new Date(), Action.UPDATE.getValue()));
        Assert.assertNotEquals(0, result);
    }

    /**
     * Method: get(long id)
     */
    @Test
    public void testGet() throws Exception {
        ReviewRequest reviewRequest = reviewService.get(1);
        Assert.assertNotNull(reviewRequest);
    }

    /**
     * Method: rejectIfExpired(ReviewRequest reviewRequest)
     */
    @Test
    public void testRejectIfExpired() throws Exception {
        reviewService.rejectIfExpired(new ReviewRequest("local_test", "/test/sub1", 0, 0, ReviewStatus.ACCEPTED.getValue(), "testuser",
                "testuser", new Date(), Action.UPDATE.getValue()));
    }

    /**
     * Method: delete(long id)
     */
    @Test
    public void testDelete() throws Exception {
        int result = reviewService.delete(1);
        Assert.assertEquals(RESULT_OK, result);
    }

} 
