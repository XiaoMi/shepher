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

import java.util.Set;

/**
 * Created by banchuanyu on 16-8-2.
 */
public interface MailSender {
    void noticeUpdate(Set<String> receivers, String creator, String path, String cluster, String link);

    void noticeReview(String receiver, String creator, String path, String cluster, String link, String action);

    void noticeDelete(Set<String> receivers, String creator, String path, String cluster, String link);

    void noticeJoinTeam(Set<String> receivers, String creator, String teamName, String link);

    void noticeJoinTeamHandled(Set<String> receivers, String creator, Status status, String teamName, String link);
}