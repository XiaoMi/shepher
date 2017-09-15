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

import com.xiaomi.shepher.model.Snapshot;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by banchuanyu on 16-6-14.
 */
public class ReviewUtil {

    public static final int NEW_CREATE_VERSION = 0;

    public static final String DEFAULT_CREATOR = "AUTO_CREATED";
    public static final String DEFAULT_REVIEWER = "AUTO_REVIEWED";
    public static final long DEFAULT_MTIME = 946656000000L; //2000-01-01 00:00:00
    public static final int DEFAULT_PAGINATION_LIMIT = 10;
    public static final int MAX_PAGINATION_LIMIT = 100;

    public static final String EMPTY_CONTENT = "";

    public static void generateSummary(List<Snapshot> snapshotList) {
        for (Snapshot snapshot : snapshotList) {
            snapshot.setContent(generateSummary(snapshot.getContent()));
        }
    }

    public static String generateSummary(String content) {
        String summary = StringEscapeUtils.escapeXml(content);
        summary = StringUtils.substring(summary, 0, 50);
        return summary;
    }

}
