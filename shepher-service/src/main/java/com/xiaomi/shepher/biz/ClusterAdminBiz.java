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

import com.xiaomi.shepher.common.DaoValidator;
import com.xiaomi.shepher.dao.ClusterAdminMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Cluster;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhangpeng on 16-11-7.
 */

@Service
public class ClusterAdminBiz {

    @Autowired
    private ClusterAdminMapper clusterAdminMapper;

    public void create(String name, String config) throws ShepherException {
        if (StringUtils.isBlank(config) || StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }

        int count; 
        try {
            count = clusterAdminMapper.create(new Cluster(name, config));
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBDeleteErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
    }

    public void update(String name, String config) throws ShepherException {
        if (StringUtils.isBlank(config) || StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }

        int count;
        try {
            count = clusterAdminMapper.update(name, config);
        } catch (Exception e) {
            throw ShepherException.createDBUpdateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_UPDATE);
    }

    public void delete(String name) throws ShepherException {
        if (StringUtils.isBlank(name)) {
            throw ShepherException.createIllegalParameterException();
        }

        int count;
        try {
            count = clusterAdminMapper.delete(name);
        } catch (Exception e) {
            throw ShepherException.createDBDeleteErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_DELETE);
    }

    public List<Cluster> all() {
        return clusterAdminMapper.all();
    }
}
