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

package com.xiaomi.shepher.configuration;

import com.xiaomi.shepher.interceptor.AuthorizationInterceptor;
import com.xiaomi.shepher.interceptor.LoginRequiredInterceptor;
import com.xiaomi.shepher.interceptor.NavInterceptor;
import com.xiaomi.shepher.interceptor.PassportInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Registers all interceptors to {@link InterceptorRegistry}.
 *
 * Created by weichuyang on 16-7-4.
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public NavInterceptor getNavInterceptor() {
        return new NavInterceptor();
    }

    @Bean
    public AuthorizationInterceptor getAuthorizationInterceptor() {
        return new AuthorizationInterceptor();
    }

    @Bean
    public PassportInterceptor getPassportInterceptor() {
        return new PassportInterceptor();
    }

    @Bean
    public LoginRequiredInterceptor getLoginRequiredInterceptor() {
        return new LoginRequiredInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPassportInterceptor());
        registry.addInterceptor(getLoginRequiredInterceptor());
        registry.addInterceptor(getAuthorizationInterceptor());
        registry.addInterceptor(getNavInterceptor());
        super.addInterceptors(registry);
    }

}
