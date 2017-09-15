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

import com.xiaomi.shepher.common.Status;

import java.util.Date;

/**
 * Created by weichuyang on 16-7-11.
 */
public class PermissionTeam {
    private long id;
    private long permissionId;
    private long teamId;
    private int statusValue;
    private Date time;

    //no mapping mysql fields,custom attributes for view
    private String cluster;
    private String path;
    private String teamName;
    private Status status;

    public PermissionTeam() {
    }

    public PermissionTeam(long permissionId, long teamId, int statusValue) {
        this.permissionId = permissionId;
        this.teamId = teamId;
        this.statusValue = statusValue;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public long getTeamId() {
        return teamId;
    }

    public void setTeamId(long teamId) {
        this.teamId = teamId;
    }

    public int getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(int statusValue) {
        this.statusValue = statusValue;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "PermissionTeam{" +
                "id=" + id +
                ", permissionId=" + permissionId +
                ", teamId=" + teamId +
                ", statusValue=" + statusValue +
                ", time=" + time +
                ", cluster='" + cluster + '\'' +
                ", path='" + path + '\'' +
                ", teamName='" + teamName + '\'' +
                ", status=" + status +
                '}';
    }
}
