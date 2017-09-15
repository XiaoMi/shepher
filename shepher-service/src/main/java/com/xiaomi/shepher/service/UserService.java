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

import com.xiaomi.shepher.biz.UserBiz;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class UserService {

    @Autowired
    private UserBiz userBiz;

    public User get(long id) {
        return userBiz.getById(id);
    }

    public User get(String name) throws ShepherException {
        return userBiz.getByName(name);
    }

    public User create(String name) throws ShepherException {
        return userBiz.create(name);
    }

    public User createIfNotExist(String name) throws ShepherException {
        User user = this.get(name);
        if (user == null) {
            user = this.create(name);
        }
        return user;
    }

    public List<User> listByTeams(Set<Long> teams, Status status, Role role) throws ShepherException {
        return userBiz.list(teams, status, role);
    }

    public Set<String> listNamesByTeams(Set<Long> teamIds, Status status, Role role) throws ShepherException {
        return userBiz.listNames(teamIds, status, role);
    }

}
