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

package com.xiaomi.shepher.biz;

import com.xiaomi.shepher.dao.NodeDAO;
import com.xiaomi.shepher.dao.SnapshotMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Snapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by banchuanyu on 16-8-9.
 */
@Service
public class NodeBiz {

    @Autowired
    private SnapshotMapper snapshotMapper;

    @Autowired
    private NodeDAO nodeDAO;

    public void update(String cluster, String path, String data, long snapshotId) throws ShepherException {
        Snapshot snapshot = snapshotMapper.get(snapshotId);
        nodeDAO.update(cluster, path, data, snapshot == null ? null : (snapshot.getZkVersion() - 1));
    }

    public void update(String cluster, String path, String data) throws ShepherException {
        nodeDAO.update(cluster, path, data, null);
    }

    public void create(String cluster, String path, String data) throws ShepherException {
        nodeDAO.create(cluster, path, data);
    }

    public void create(String cluster, String path, String data, boolean createParents)throws ShepherException {
        nodeDAO.create(cluster, path, data, createParents);
    }

    public void createEphemeral(String cluster, String path, String data) throws ShepherException{
        nodeDAO.createEphemeral(cluster, path, data);
    }

    public void delete(String cluster, String path) throws ShepherException {
        nodeDAO.delete(cluster, path);
    }
}
