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

import com.xiaomi.shepher.model.Cluster;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Mapper
public interface ClusterAdminMapper {

    @Insert("INSERT INTO cluster(name, config) VALUES (#{name}, #{config})")
    int create(Cluster cluster);

    @Update("UPDATE cluster SET config = #{config} WHERE name = #{name}")
    int update(@Param("name") String name, @Param("config") String config);

    @Delete("DELETE FROM cluster WHERE name = #{name}")
    int delete(@Param("name") String name);

    @Select("SELECT id, name, config, time FROM cluster")
    List<Cluster> all();

}
