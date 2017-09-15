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

package com.xiaomi.shepher.interceptor;

import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.model.Cluster;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.util.AuthStrategy;
import com.xiaomi.shepher.util.ClusterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link NavInterceptor} inject basic data to nav bar.
 *
 * Created by weichuyang on 16-7-8.
 */
public class NavInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserHolder userHolder;

    @Autowired
    private AuthStrategy authStrategy;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        List<Cluster> clusters = ClusterUtil.getClusters();
        User user = userHolder.getUser();
        modelAndView.addObject("clusters", clusters);
        modelAndView.addObject("user", user);
        modelAndView.addObject("isAdmin", authStrategy.isAdmin());

        super.postHandle(request, response, handler, modelAndView);
    }
}
