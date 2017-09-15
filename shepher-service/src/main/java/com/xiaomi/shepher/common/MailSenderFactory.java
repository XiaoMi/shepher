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

import com.xiaomi.shepher.exception.ShepherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by banchuanyu on 16-11-15.
 */
@Service
public class MailSenderFactory {

    // customBean and generalBean mapped to property mail.sender in application.properties
    private static final String CUSTOM_BEAN = "customMailSender";
    private static final String GENERAL_BEAN = "generalMailSender";

    @Value("${mail.sender:generalMailSender}")
    private String mailSender;

    @Autowired
    @Qualifier(CUSTOM_BEAN)
    private MailSender customMailSender;

    @Autowired
    @Qualifier(GENERAL_BEAN)
    private MailSender generalMailSender;

    public MailSender getMailSender() throws ShepherException {
        if (mailSender.equals(CUSTOM_BEAN)) {
            return customMailSender;
        } else if (mailSender.equals(GENERAL_BEAN)) {
            return generalMailSender;
        } else {
            throw ShepherException.createIllegalParameterException();
        }
    }

}
