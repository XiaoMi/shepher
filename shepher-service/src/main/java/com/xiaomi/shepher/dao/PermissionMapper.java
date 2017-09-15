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

package com.xiaomi.shepher.dao;

import com.xiaomi.shepher.model.Permission;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by banchuanyu on 16-11-3.
 */
@Mapper
public interface PermissionMapper {
    @Select("SELECT id, cluster, path, time FROM permission WHERE cluster = #{cluster} AND path = #{path}")
    Permission get(@Param("cluster") String cluster, @Param("path") String path);

    @Insert("INSERT INTO permission(cluster, path ) VALUES (#{cluster}, #{path})")
    int create(Permission permission);
}
