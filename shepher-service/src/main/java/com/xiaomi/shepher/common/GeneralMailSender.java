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
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zhangpeng on 16-11-11.
 */
@Service("generalMailSender")
public class GeneralMailSender extends MailSenderAbstract {

    private static final Logger logger = LoggerFactory.getLogger(GeneralMailSender.class);

    @Value("${mail.hostname:}")
    private String hostname;

    @Value("${mail.port:25}")
    private int port;

    @Value("${mail.username:}")
    private String username;

    @Value("${mail.password:}")
    private String password;

    @Value("${mail.from:}")
    private String from;

    @Value("${mail.fromname:}")
    private String fromname;

    protected void send(String mailAddress, String title, String content) {
        if (StringUtils.isBlank(mailAddress)) {
            return;
        }

        try {
            Email email = new HtmlEmail();
            email.setHostName(hostname);
            email.setAuthenticator(new DefaultAuthenticator(username, password));
            email.setSmtpPort(port);
            email.setFrom(from, fromname);
            email.setSubject(title);
            email.setMsg(content);
            email.addTo(mailAddress.split(mailAddressEndSeparator));
            email.send();
        } catch (Exception e) {
            logger.error("Send Mail Error", e);
        }
    }
}
