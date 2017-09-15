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

package com.xiaomi.shepher.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by banchuanyu on 16-8-2.
 */
@Service("customMailSender")
public class CustomMailSender extends MailSenderAbstract {

    private static final Logger logger = LoggerFactory.getLogger(CustomMailSender.class);

    @Value("${mail.mailToSuffix:}")
    private String mailToSuffix;

    protected void send(String mailAddress, String title, String content) {
        if (StringUtils.isBlank(mailAddress)) {
            return;
        }
        // TODO: use your own mail sender
    }
}