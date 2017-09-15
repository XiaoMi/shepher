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

import com.xiaomi.shepher.biz.PermissionBiz;
import com.xiaomi.shepher.biz.PermissionTeamBiz;
import com.xiaomi.shepher.biz.TeamBiz;
import com.xiaomi.shepher.common.Role;
import com.xiaomi.shepher.common.Status;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.PermissionTeam;
import com.xiaomi.shepher.util.ClusterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class PermissionService {
    private static final Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private PermissionTeamBiz permissionTeamBiz;

    @Autowired
    private TeamBiz teamBiz;

    @Autowired
    private PermissionBiz permissionBiz;

    public PermissionTeam add(long permissionId, long teamId, Status status) throws ShepherException {
        return permissionTeamBiz.create(permissionId, teamId, status);
    }

    @Transactional
    public boolean add(long teamId, String cluster, String path, Status status) throws ShepherException {
        long permissionId = permissionBiz.createIfNotExists(cluster, path);
        this.add(permissionId, teamId, status);
        return true;
    }

    public boolean addPending(long teamId, String cluster, String path) throws ShepherException {
        return this.add(teamId, cluster, path, Status.PENDING);
    }

    public boolean addAgree(long teamId, String cluster, String path) throws ShepherException {
        return this.add(teamId, cluster, path, Status.AGREE);
    }

    public List<PermissionTeam> listPermissionTeamsAgree() throws ShepherException {
        return permissionTeamBiz.list(Status.AGREE);
    }

    public List<PermissionTeam> listPermissionTeamsByTeam(long teamId, Status status) throws ShepherException {
        return permissionTeamBiz.listByTeam(teamId, status);
    }

    public List<PermissionTeam> listPermissionTeamsPending() throws ShepherException {
        return permissionTeamBiz.list(Status.PENDING);
    }

    public int updateStatus(long id, Status status) throws ShepherException {
        return permissionTeamBiz.update(id, status);
    }

    public boolean isPathMember(long userId, String cluster, String path) throws ShepherException {
        if (ClusterUtil.isPublicCluster(cluster)) {
            return true;
        }
        return teamBiz.isAboveRole(cluster, path, Role.MEMBER, userId);
    }

    public boolean isPathMaster(long userId, String cluster, String path) throws ShepherException {
        if (ClusterUtil.isPublicCluster(cluster)) {
            return true;
        }
        return teamBiz.isAboveRole(cluster, path, Role.MASTER, userId);
    }

    public boolean isPathMaster(String userName, String cluster, String path) throws ShepherException {
        if (ClusterUtil.isPublicCluster(cluster)) {
            return true;
        }
        return teamBiz.isAboveRole(cluster, path, Role.MASTER, userName);
    }

}
