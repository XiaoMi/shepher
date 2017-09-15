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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by weichuyang on 16-7-8.
 */
public enum Role {
    MASTER(100, "master"),

    DEVELOPER(60, "developer"),

    MEMBER(10, "member");

    private static final Map<Integer, Role> LOOKUP = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            LOOKUP.put(role.getValue(), role);
        }
    }

    private int value;
    private String description;

    Role(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Role get(int value) {
        return LOOKUP.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
