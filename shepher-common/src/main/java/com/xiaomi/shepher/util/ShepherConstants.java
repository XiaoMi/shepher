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

/**
 * Created by zhangpeng on 16-11-2.
 */
public interface ShepherConstants {

    String DB_OPERATE_CREATE = "create";
    String DB_OPERATE_UPDATE = "update";
    String DB_OPERATE_DELETE = "delete";

    String LOGIN_TYPE_LDAP = "LDAP";
    String LOGIN_TYPE_CAS = "CAS";
    String LOGIN_TYPE_DEMO = "DEMO";

    int DEFAULT_ROLEVALUE = 0;
    String ADMIN = "admin";
}
