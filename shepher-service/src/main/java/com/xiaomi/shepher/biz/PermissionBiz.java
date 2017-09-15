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
import com.xiaomi.shepher.dao.PermissionMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Permission;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

/**
 * Created by banchuanyu on 16-8-10.
 */
@Service
public class PermissionBiz {

    @Autowired
    private PermissionMapper permissionMapper;

    public long createIfNotExists(String cluster, String path) throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path)) {
            throw ShepherException.createIllegalParameterException();
        }
        Permission permission = permissionMapper.get(cluster, path);
        try {
            if (permission == null) {
                return this.create(cluster, path).getId();
            }
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }
        return permission.getId();
    }

    public Permission create(String cluster, String path) throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path)) {
            throw ShepherException.createIllegalParameterException();
        }
        Permission permission = new Permission(cluster, path);
        int count;
        try {
            count = permissionMapper.create(permission);
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return permission;
    }

}
