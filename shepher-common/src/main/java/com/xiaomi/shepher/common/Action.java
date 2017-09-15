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
 * Created by banchuanyu on 16-6-12.
 */
public enum Action {
    ADD(10, "add"),

    DELETE(20, "delete"),

    UPDATE(30, "update");

    private int value;
    private String description;

    Action(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static Action get(int value) {
        for (Action action : Action.values()) {
            if (action.getValue() == value) {
                return action;
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
