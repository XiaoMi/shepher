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
import com.xiaomi.shepher.common.MailSenderAbstract;
import com.xiaomi.shepher.common.MailSenderFactory;
import com.xiaomi.shepher.dao.NodeDAO;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import javax.annotation.Resource;

/**
 * NodeService Tester.
 *
 * Created by banchuanyu on 16-11-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class NodeServiceTest extends BaseTest {

    @InjectMocks
    @Resource(name = "nodeService")
    private NodeService nodeService;

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
        when(nodeDAO.getStat(anyString(), anyString(), anyBoolean())).thenReturn(stat);
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
     * Method: getChildren(String cluster, String path)
     */
    @Test
    public void testGetChildren() throws Exception {
        List<String> children = nodeService.getChildren("local_test", "/test");
        Assert.assertNotNull(children);
        Assert.assertEquals(0, children.size());
    }

    /**
     * Method: getData(String cluster, String path)
     */
    @Test
    public void testGetData() throws Exception {
        String data = nodeService.getData("local_test", "/test");
        Assert.assertNull(data);
    }

    /**
     * Method: getStat(String cluster, String path)
     */
    @Test
    public void testGetStatForClusterPath() throws Exception {
        Stat stat = nodeService.getStat("local_test", "/test");
        Assert.assertNotNull(stat);
    }

    /**
     * Method: getStat(String cluster, String path, boolean returnNullIfPathNotExists)
     */
    @Test
    public void testGetStatForClusterPathReturnNullIfPathNotExists() throws Exception {
        Stat stat = nodeService.getStat("local_test", "/test", true);
        Assert.assertNotNull(stat);
    }

    /**
     * Method: create(String cluster, String path, String data, String creator)
     */
    @Test
    public void testCreate() throws Exception {
        nodeService.create("local_test", "/test", "data", "creator");
    }

    /**
     * Method: update(String cluster, String path, String data, String creator)
     */
    @Test
    public void testUpdate() throws Exception {
        nodeService.update("local_test", "/test", "", "");
    }

    /**
     * Method: delete(String cluster, String path, String creator)
     */
    @Test
    public void testDelete() throws Exception {
        nodeService.delete("local_test", "/test", "");
    }

} 
