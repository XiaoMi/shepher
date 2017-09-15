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
public enum Status {
    AGREE(10, "accepted"),

    REFUSE(-10, "refused"),

    PENDING(0, "pending"),

    DELETE(-100, "deleted");

    private static final Map<Integer, Status> LOOKUP = new HashMap<>();

    static {
        for (Status status : Status.values()) {
            LOOKUP.put(status.getValue(), status);
        }
    }

    private int value;
    private String description;

    Status(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Status get(int value) {
        return LOOKUP.get(value);
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}

