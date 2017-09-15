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

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by banchuanyu on 16-8-5.
 */
public class ParentPathParser {
    public static List<String> parse(String path) {
        if (path == null) {
            return Collections.emptyList();
        }
        List<String> parents = new ArrayList<>();
        String[] pathSegments = path.split("/");
        StringBuilder parent = new StringBuilder("");
        parents.add("/");
        for (String pathSegment : pathSegments) {
            if (StringUtils.isEmpty(pathSegment)) {
                continue;
            }
            parent.append("/");
            String[] dotPathSegment = pathSegment.split("\\.");
            for (int i = 0; i < dotPathSegment.length; i++) {
                if (i != 0) {
                    parent.append(".");
                }
                parent.append(dotPathSegment[i]);
                parents.add(parent.toString());
            }
        }
        return parents;
    }

    public static String getParent(String path) {
        int lastIndex = path.lastIndexOf('/');
        String parentPath = path.substring(0, lastIndex);
        return parentPath;
    }
}
