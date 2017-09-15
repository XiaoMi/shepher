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

import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.util.ZkPool;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Repository
public class NodeDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeDAO.class);

    public List<String> getChildren(String cluster, String path) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return Collections.emptyList();
            }
            return zkClient.getChildren(path);
        } catch (Exception e) {
            LOGGER.warn("Fail to get children, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public boolean exists(String cluster, String path) {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return false;
            }
            return zkClient.exists(path);
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public String getData(String cluster, String path) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return null;
            }
            return zkClient.readData(path);
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    private String getData(String cluster, String path, Stat stat) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return null;
            }
            return zkClient.readData(path, stat);
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to get data, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public Stat getStat(String cluster, String path, boolean returnNullIfPathNotExists) throws ShepherException {
        Stat stat = new Stat();
        try {
            this.getData(cluster, path, stat);
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to get stat, Exception:", e);
            if (!returnNullIfPathNotExists) {
                throw ShepherException.createNoNodeException();
            } else {
                stat = null;
            }
        }
        return stat;
    }

    public void create(String cluster, String path, String data) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            zkClient.createPersistent(path, data);
        } catch (ZkNodeExistsException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNodeExistsException();
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public void createWithAcl(String cluster, String path, String data) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            zkClient.create(path, data, ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
        } catch (ZkNodeExistsException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNodeExistsException();
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public void createEphemeral(String cluster, String path, String data) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            zkClient.createEphemeral(path, data);
        } catch (ZkNodeExistsException e) {
            LOGGER.warn("Fail to create ephemeral, Exception:", e);
            throw ShepherException.createNodeExistsException();
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to create ephemeral, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to create ephemeral, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public void create(String cluster, String path, String data, boolean createParents) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            if (createParents) {
                zkClient.createPersistent(path, true);
                zkClient.writeData(path, data);
            } else {
                zkClient.createPersistent(path, data);
            }
        } catch (ZkNodeExistsException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNodeExistsException();
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to create node, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public void update(String cluster, String path, String data, Integer zkVersion) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            if (zkVersion == null) {
                zkClient.writeData(path, data);
            } else {
                zkClient.writeData(path, data, zkVersion);
            }
        } catch (ZkBadVersionException e) {
            LOGGER.warn("Success to update node, but throw exception", e);
        } catch (Exception e) {
            LOGGER.warn("Fail to update node, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public void delete(String cluster, String path) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return;
            }
            zkClient.delete(path);
        } catch (ZkNoNodeException e) {
            LOGGER.warn("Fail to delete node, Exception:", e);
            throw ShepherException.createNoNodeException();
        } catch (Exception e) {
            LOGGER.warn("Fail to delete node, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }

    public long getCreationTime(String cluster, String path) throws ShepherException {
        ZkClient zkClient = ZkPool.getZkClient(cluster);
        try {
            if (zkClient == null) {
                return -1L;
            }
            return zkClient.getCreationTime(path);
        } catch (Exception e) {
            LOGGER.warn("Fail to get creation time, Exception:", e);
            throw ShepherException.createUnknownException();
        } finally {
            ZkPool.releaseZkClient(cluster, zkClient);
        }
    }
}
