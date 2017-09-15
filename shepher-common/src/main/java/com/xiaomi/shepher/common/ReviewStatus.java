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

/**
 * Created by banchuanyu on 16-7-28.
 */
public enum ReviewStatus {
    NEW(0, "new"),

    ACCEPTED(10, "accepted"),

    REJECTED(-10, "rejected"),

    DELETED(-100, "deleted");

    private int value;
    private String description;

    ReviewStatus(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static ReviewStatus get(int value) {
        for (ReviewStatus reviewStatus : ReviewStatus.values()) {
            if (reviewStatus.getValue() == value) {
                return reviewStatus;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

}
