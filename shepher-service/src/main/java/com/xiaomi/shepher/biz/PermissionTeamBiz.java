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
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.dao.PermissionTeamMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.PermissionTeam;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by banchuanyu on 16-8-9.
 */
@Service
public class PermissionTeamBiz {

    @Autowired
    private PermissionTeamMapper permissionTeamMapper;

    public PermissionTeam get(long team, String cluster, String path, Status status) throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path) || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return permissionTeamMapper.get(team, cluster, path, status.getValue());
    }

    public List<PermissionTeam> list(Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return permissionTeamMapper.list(status.getValue());
    }

    public List<PermissionTeam> listByTeam(long team, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return permissionTeamMapper.listByTeam(team, status.getValue());
    }

    public int update(long id, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        int count;
        try {
            count = permissionTeamMapper.update(id, status.getValue());
        } catch (Exception e) {
            throw ShepherException.createDBUpdateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_UPDATE);
        return count;
    }

    public PermissionTeam create(long permissionId, long teamId, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        PermissionTeam permissionTeam = new PermissionTeam(permissionId, teamId, status.getValue());
        int count;
        try {
            count = permissionTeamMapper.create(permissionTeam);
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return permissionTeam;
    }

}
