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

package com.xiaomi.shepher.util;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangpeng on 16-11-3.
 */
public class ZkPool {
    private static final Logger logger = LoggerFactory.getLogger(ZkPool.class);
    private static final int DEFAULT_CONNECTION_TIMEOUT = 30000;
    private static final int DEFAULT_SESSION_TIMEOUT = 30000;
    private static final long ZKPOOL_KEEPSTATE_TIME = 1000L;

    private static KeyedObjectPool<String, ZkClient> zkClientFactory = new GenericKeyedObjectPool<>(new ZkClientFactory());

    public static ZkClient getZkClient(String key) {
        Map<String, String> clusterMap = ClusterUtil.getClusterMap();
        try {
            if (key != null && clusterMap.containsKey(key)) {
                return zkClientFactory.borrowObject(clusterMap.get(key));
            }
        } catch (Exception e) {
            logger.error("Get zkClient error", e);
        }
        return null;
    }

    public static void releaseZkClient(String key, ZkClient zkClient) {
        Map<String, String> clusterMap = ClusterUtil.getClusterMap();
        try {
            if (key != null && clusterMap.containsKey(key) && zkClient != null) {
                zkClientFactory.returnObject(clusterMap.get(key), zkClient);
            }
        } catch (Exception e) {
            logger.error("Release zkClient error", e);
        }
    }

    /**
     * Apache common pool zkClientFactory.
     */
    private static class ZkClientFactory extends BaseKeyedPooledObjectFactory<String, ZkClient> {

        @Override
        public ZkClient create(String config) throws Exception {
            if (config != null) {
                return new ZkClient(config, DEFAULT_CONNECTION_TIMEOUT, DEFAULT_SESSION_TIMEOUT, new StringSerializer());
            }
            return null;
        }

        @Override
        public PooledObject<ZkClient> wrap(ZkClient zkClient) {
            return new DefaultPooledObject<>(zkClient);
        }

        @Override
        public void destroyObject(String key, PooledObject<ZkClient> pooledObject) throws Exception {
            pooledObject.getObject().close();
        }

        @Override
        public boolean validateObject(String key, PooledObject<ZkClient> pooledObject) {
            return pooledObject.getObject().waitUntilConnected(ZKPOOL_KEEPSTATE_TIME, TimeUnit.MILLISECONDS);
        }

    }

}
