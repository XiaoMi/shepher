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

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by zhangpeng on 16-11-15.
 */

public abstract class MailSenderAbstract implements MailSender {
    @Value("${mail.mailToSuffix:}")
    private String mailToSuffix;

    @Value("${mail.mailAddressEndSeparator:}")
    protected String mailAddressEndSeparator;

    private static final String TITLE_UPDATE = "[Shepher Update] %s updates node %s in cluster %s";
    private static final String CONTENT_UPDATE = "You can review it here:<a href=\"%s\">%s</a>";

    private static final String TITLE_REVIEW = "[Shepher Review] %s %s review of node %s in cluster %s";
    private static final String CONTENT_REVIEW = "You can check it here:<a href=\"%s\">%s</a>";

    private static final String TITLE_DELETE = "[Shepher Delete] %s deleted node %s in cluster %s";
    private static final String CONTENT_DELETE = "You can check it here:<a href=\"%s\">%s</a>";

    private static final String TITLE_JOINTEAM = "[Shepher Team] %s apply to join team %s";
    private static final String CONTENT_JOINTEAM = "You can check it here:<a href=\"%s\">%s</a>";

    private static final String TITLE_JOINTEAMHANDLED = "[Shepher Team] %s %s to join you in team %s";
    private static final String CONTENT_JOINTEAMHANDLED = "You can check it here:<a href=\"%s\">%s</a>";

    @Override
    public void noticeUpdate(Set<String> receivers, String creator, String path, String cluster, String link) {
        this.send(getMailAddress(creator, receivers), String.format(TITLE_UPDATE, creator, path, cluster),
                String.format(CONTENT_UPDATE, link, link));
    }

    @Override
    public void noticeReview(String receiver, String creator, String path, String cluster, String link, String action) {
        this.send(getMailAddress(creator, receiver), String.format(TITLE_REVIEW, creator, action, path, cluster),
                String.format(CONTENT_REVIEW, link, link));
    }

    @Override
    public void noticeDelete(Set<String> receivers, String creator, String path, String cluster, String link) {
        this.send(getMailAddress(creator, receivers), String.format(TITLE_DELETE, creator, path, cluster),
                String.format(CONTENT_DELETE, link, link));
    }

    @Override
    public void noticeJoinTeam(Set<String> receivers, String creator, String teamName, String link) {
        this.send(getMailAddress(creator, receivers), String.format(TITLE_JOINTEAM, creator, teamName),
                String.format(CONTENT_JOINTEAM, link, link));
    }

    @Override
    public void noticeJoinTeamHandled(Set<String> receivers, String creator, Status status, String teamName, String link) {
        this.send(getMailAddress(creator, receivers), String.format(TITLE_JOINTEAMHANDLED, creator, status.getDescription(), teamName),
                String.format(CONTENT_JOINTEAMHANDLED, link, link));
    }

    protected String getMailAddress(String creator, Set<String> receivers) {
        return getMailAddress(creator, Arrays.copyOf(receivers.toArray(), receivers.size(), String[].class));
    }

    protected String getMailAddress(String creator, String... receivers) {
        StringBuilder mailAddress = new StringBuilder();
        mailAddress.append(creator).append(mailToSuffix);
        for (String receiver : receivers) {
            if (mailAddress.indexOf(receiver) != -1) {
                continue;
            }

            mailAddress.append(mailAddressEndSeparator).append(receiver).append(mailToSuffix);
        }

        return mailAddress.toString();
    }

    protected abstract void send(String mailAddress, String title, String content);
}
