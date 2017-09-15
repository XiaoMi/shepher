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

package com.xiaomi.shepher.model;

import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;

import java.util.Date;

/**
 * Created by weichuyang on 16-7-5.
 */
public class UserTeam {
    private long id;
    private long userId;
    private long teamId;
    private int roleValue;
    private int statusValue;
    private Date time;

    //no mapping mysql fields,custom attributes for view
    private String teamName;
    private String userName;
    private Role role;
    private Status status;

    public UserTeam() {
    }

    public UserTeam(long userId, long teamId, int roleValue, int statusValue) {
        this.userId = userId;
        this.teamId = teamId;
        this.roleValue = roleValue;
        this.statusValue = statusValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public int getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(int roleValue) {
        this.roleValue = roleValue;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
    }

    public Role getRole() {
        if (role == null) {
            role = Role.get(this.roleValue);
        }
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        if (status == null) {
            status = Status.get(this.statusValue);
        }
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "UserTeam{" +
                "id=" + id +
                ", userId=" + userId +
                ", teamId=" + teamId +
                ", roleValue=" + roleValue +
                ", statusValue=" + statusValue +
                ", time=" + time +
                ", teamName='" + teamName + '\'' +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}
