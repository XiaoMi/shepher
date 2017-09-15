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

package com.xiaomi.shepher.util;

import com.xiaomi.shepher.common.Jurisdiction;
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.service.PermissionService;
import com.xiaomi.shepher.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by weichuyang on 16-7-14.
 */
@Component
public class AuthStrategy {

    private static final Logger logger = LoggerFactory.getLogger(AuthStrategy.class);
    private static final Map<Integer, Pattern> URL_PATTERNS = new HashMap<>();
    private static final Map<Jurisdiction, String> AUTH_METHOD_MAPPING = new EnumMap<>(Jurisdiction.class);

    private static final int TEAM_PATTERNS = 1;
    private static final int CLUSTERS_PATTERNS = 2;

    static {
        URL_PATTERNS.put(TEAM_PATTERNS, Pattern.compile("^/teams/(\\d+)"));
        URL_PATTERNS.put(CLUSTERS_PATTERNS, Pattern.compile("^/clusters/(\\S+)/nodes"));

        AUTH_METHOD_MAPPING.put(Jurisdiction.SUPER_ADMIN, "isAdmin");
        AUTH_METHOD_MAPPING.put(Jurisdiction.TEAM_MASTER, "isTeamMaster");
        AUTH_METHOD_MAPPING.put(Jurisdiction.TEAM_OWNER, "isTeamOwner");
        AUTH_METHOD_MAPPING.put(Jurisdiction.TEAM_MEMBER, "isTeamMember");
    }

    @Autowired
    public TeamService teamService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserHolder userHolder;

    public boolean validate(HttpServletRequest request, Jurisdiction jurisdiction) throws ShepherException {
        boolean hasAuth;
        String authMethodName = AUTH_METHOD_MAPPING.get(jurisdiction);
        try {
            Method method = this.getClass().getDeclaredMethod(authMethodName, HttpServletRequest.class);
            hasAuth = (boolean) method.invoke(this, request);
        } catch (Exception e) {
            logger.error("Permission validation exception", e);
            throw ShepherException.createNoAuthorizationException();
        }

        return hasAuth;
    }

    public boolean isAdmin() throws ShepherException {
        User user = userHolder.getUser();
        return user != null && teamService.isAdmin(user.getId());
    }

    private boolean isAdmin(HttpServletRequest request) throws ShepherException {
        return isAdmin();
    }

    private boolean isTeamMaster(HttpServletRequest request) throws ShepherException {
        String url = request.getServletPath();
        Matcher teamMatcher = URL_PATTERNS.get(TEAM_PATTERNS).matcher(url);
        Matcher clusterMatcher = URL_PATTERNS.get(CLUSTERS_PATTERNS).matcher(url);
        if (teamMatcher.find()) {
            long teamId = Long.parseLong(teamMatcher.group(1));
            long userId = userHolder.getUser().getId();
            return teamService.isOwner(userId, teamId) || teamService.isMaster(userId, teamId);
        }
        if (clusterMatcher.find()) {
            String cluster = clusterMatcher.group(1);
            String path = request.getParameter("path");
            long userId = userHolder.getUser().getId();
            return permissionService.isPathMaster(userId, cluster, path);
        }
        logger.error("Illegal path exception", request);
        throw ShepherException.createIllegalPathException();
    }

    private boolean isTeamMember(HttpServletRequest request) throws ShepherException {
        String url = request.getServletPath();
        Matcher matcher = URL_PATTERNS.get(CLUSTERS_PATTERNS).matcher(url);
        if (matcher.find()) {
            String cluster = matcher.group(1);
            String path = request.getParameter("path");
            long userId = userHolder.getUser().getId();
            return permissionService.isPathMember(userId, cluster, path);
        }
        logger.error("Illegal path exception", request);
        throw ShepherException.createIllegalPathException();
    }

}
