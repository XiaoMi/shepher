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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by banchuanyu on 16-8-15.
 */
public class IpPathFilter {
    public static List<String> filter(List<String> nodes, String showIp) {
        Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)");
        List<String> result = new LinkedList<>();
        List<String> ipResult = new ArrayList<>();

        String[] showIps = showIp.split(",");
        Set<String> allowIpSet = new HashSet<>();
        for (String ip : showIps) {
            allowIpSet.add(ip.trim());
        }

        for (String node : nodes) {
            Matcher matcher = pattern.matcher(node);
            if (matcher.matches()) {
                if (allowIpSet.contains(node)) {
                    result.add(0, node);
                }
            } else {
                result.add(node);
            }
        }
        result.addAll(ipResult);

        return result;
    }
}
