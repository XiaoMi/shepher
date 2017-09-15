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

import com.xiaomi.shepher.common.Auth;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.util.AuthStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The {@link AuthorizationInterceptor} checks user's authorization.
 *
 * Created by weichuyang on 16-7-14.
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthStrategy authStrategy;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Auth annotation = getAnnotation(handlerMethod);

            if (annotation != null && !authStrategy.validate(request, annotation.value())) {
                throw ShepherException.createNoAuthorizationException();
            }
        }

        return true;
    }

    private Auth getAnnotation(HandlerMethod handlerMethod) {
        Auth annotation = handlerMethod.getMethodAnnotation(Auth.class);

        Class<?> beanType = handlerMethod.getBeanType();
        if (beanType.isAnnotationPresent(Auth.class)) {
            annotation = beanType.getAnnotation(Auth.class);
        }
        return annotation;
    }

}
