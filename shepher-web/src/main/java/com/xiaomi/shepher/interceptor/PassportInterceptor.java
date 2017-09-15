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
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.User;
import com.xiaomi.shepher.service.UserService;
import com.xiaomi.shepher.util.ShepherConstants;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link PassportInterceptor} read user's data and inject it to {@link UserHolder}.
 *
 * Created by banchuanyu on 16-7-21.
 */
public class PassportInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PassportInterceptor.class);

    @Autowired
    UserService userService;

    @Autowired
    private UserHolder userHolder;

    @Value("${server.login.type}")
    private String loginType;

    @Value("${demo.admin.name}")
    private String demoAdminName;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        return holdUser(request);
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        userHolder.clean();
    }

    private boolean holdUser(HttpServletRequest request)
            throws ShepherException {
        return holdBrowserTerminalUser(request);
    }

    private boolean holdBrowserTerminalUser(HttpServletRequest request)
            throws ShepherException {
        String userName = null;
        if (ShepherConstants.LOGIN_TYPE_LDAP.equals(loginType.toUpperCase())) {
            SecurityContextImpl context =
                    (SecurityContextImpl) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            if (context != null) {
                userName = ((LdapUserDetailsImpl) context.getAuthentication().getPrincipal()).getUsername();
            }
        } else if (ShepherConstants.LOGIN_TYPE_CAS.equals(loginType.toUpperCase())) {
            Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
            userName = assertion.getPrincipal().getName();
        } else if (ShepherConstants.LOGIN_TYPE_DEMO.equals(loginType.toUpperCase())) {
            SecurityContextImpl context =
                    (SecurityContextImpl) request.getSession().getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
            if (context != null) {
                userName = ((UserDetails) context.getAuthentication().getPrincipal()).getUsername();
            }
        } else {
            throw ShepherException.createIllegalLoginTypeException();
        }

        if (userName != null) {
            User user = userService.createIfNotExist(userName);
            userHolder.setUser(user);
            String requestURI = request.getRequestURI();
            String httpMethod = request.getMethod();
            logger.info("userId:{}, userName:{}, {}:{}", user.getId(), user.getName(), httpMethod, requestURI);
        }

        return true;
    }

}
