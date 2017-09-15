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

import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.DaoValidator;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.dao.NodeDAO;
import com.xiaomi.shepher.dao.SnapshotMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Snapshot;
import com.xiaomi.shepher.util.ReviewUtil;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by banchuanyu on 16-8-8.
 */
@Service
public class SnapshotBiz {

    @Autowired
    private SnapshotMapper snapshotMapper;

    @Autowired
    private NodeDAO nodeDAO;

    public List<Snapshot> listByPath(String path, String cluster, int offset, int limit) throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path)) {
            throw ShepherException.createIllegalParameterException();
        }
        return snapshotMapper.listByPath(path, cluster, offset, limit);
    }

    public Snapshot getByPathAndZk(String path, String cluster, long zkMtime, int zkVersion) throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path)) {
            throw ShepherException.createIllegalParameterException();
        }
        return snapshotMapper.getByPathAndZk(path, cluster, new Date(zkMtime), zkVersion);
    }

    /**
     * Get original snapshot and return id.
     *
     * @param path
     * @param cluster
     * @param creator
     * @param stat
     * @param action
     * @param createIfNotExists
     * @return snapshotId
     * @throws ShepherException
     */
    public long getOriginalId(String path, String cluster, String creator, Stat stat, Action action, boolean createIfNotExists)
            throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path) || stat == null) {
            throw ShepherException.createIllegalParameterException();
        }
        boolean exists = nodeDAO.exists(cluster, path);
        if (!exists) {
            throw ShepherException.createNoSuchNodeException();
        }

        long mtime = stat.getMtime();
        if (mtime == 0) {
            mtime = ReviewUtil.DEFAULT_MTIME;
        }

        Snapshot snapshot = this.getByPathAndZk(path, cluster, mtime, stat.getVersion());
        long snapshotId = 0L;
        if (snapshot != null) {
            snapshotId = snapshot.getId();
        } else {
            if (createIfNotExists) {
                String oldData = nodeDAO.getData(cluster, path);
                snapshotId = this.create(cluster, path, oldData, creator, action, mtime, ReviewStatus.ACCEPTED, stat.getVersion(),
                        ReviewUtil.DEFAULT_REVIEWER).getId();
            }
        }
        return snapshotId;
    }

    public Snapshot create(String cluster, String path, String content, String creator, Action action, long zkMtime,
                           ReviewStatus reviewStatus, int zkVersion, String reviewer)
            throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path) || action == null || reviewStatus == null) {
            throw ShepherException.createIllegalParameterException();
        }
        Snapshot snapshot = new Snapshot(cluster, path, content, creator, action.getValue(), new Date(zkMtime), reviewStatus.getValue(),
                zkVersion, reviewer);
        int count;
        try {
            count = snapshotMapper.create(snapshot);
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return snapshot;
    }

    public int update(long id, ReviewStatus reviewStatus, String reviewer, long zkMtime) throws ShepherException {
        if (StringUtils.isBlank(reviewer) || reviewStatus == null) {
            throw ShepherException.createIllegalParameterException();
        }
        try {
            return snapshotMapper.update(id, reviewStatus.getValue(), reviewer, new Date(zkMtime));
        } catch (Exception e) {
            throw ShepherException.createDBUpdateErrorException();
        }
    }

    public Snapshot get(long id) {
        return snapshotMapper.get(id);
    }

    public int delete(long id) throws ShepherException {
        int count;
        try {
            count = snapshotMapper.delete(id);
        } catch (Exception e) {
            throw ShepherException.createDBDeleteErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_DELETE);
        return count;
    }

}
