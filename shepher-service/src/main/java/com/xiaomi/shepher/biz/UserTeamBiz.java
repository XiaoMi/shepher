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
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.dao.UserTeamMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.UserTeam;
import com.xiaomi.shepher.util.ShepherConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by banchuanyu on 16-8-9.
 */
@Service
public class UserTeamBiz {

    @Autowired
    private UserTeamMapper userTeamMapper;

    public UserTeam get(long id) {
        return userTeamMapper.get(id);
    }

    public UserTeam create(long userId, long teamId, Role role, Status status) throws ShepherException {
        if (role == null || status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        UserTeam userTeam = new UserTeam(userId, teamId, role.getValue(), status.getValue());
        int count = userTeamMapper.create(userTeam);
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return userTeam;
    }

    public int updateStatus(long id, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        int count = userTeamMapper.updateStatus(id, status.getValue());
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_UPDATE);
        return count;
    }

    public int updateRole(long id, Role role) throws ShepherException {
        if (role == null) {
            throw ShepherException.createIllegalParameterException();
        }
        int count = userTeamMapper.updateRole(id, role.getValue());
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_UPDATE);
        return count;
    }

    public int getRoleValue(long user, long team, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        Integer role = userTeamMapper.getRoleValue(user, team, status.getValue());
        return role == null ? 0 : role;
    }

    public List<UserTeam> listByTeam(long team, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return userTeamMapper.listByTeam(team, status.getValue());
    }

    public List<UserTeam> listByUser(long user, Status status) throws ShepherException {
        if (status == null) {
            throw ShepherException.createIllegalParameterException();
        }
        return userTeamMapper.listByUser(user, status.getValue());
    }

    public List<String> listUserByTeamId(long team) throws ShepherException {
        return userTeamMapper.listUserByTeamId(team);
    }

}
