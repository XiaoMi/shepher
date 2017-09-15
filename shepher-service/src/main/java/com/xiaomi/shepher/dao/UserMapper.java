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

import com.xiaomi.shepher.dao.mybatis.MybatisExtendedLanguageDriver;
import com.xiaomi.shepher.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * Created by banchuanyu on 16-11-2.
 */
@Mapper
public interface UserMapper {

    @Select("SELECT id, name, time FROM user WHERE name = #{name}")
    User getByName(@Param("name") String name);

    @Select("SELECT id, name, time FROM user WHERE id = #{id}")
    User getById(@Param("id") long id);

    @Lang(MybatisExtendedLanguageDriver.class)
    @Select("SELECT DISTINCT user.id, user.name, user.time FROM user, user_team WHERE user_team.team_id IN (#{teams}) " +
            "AND user_team.user_id = user.id AND user_team.status >= #{status} AND user_team.role >= #{role}")
    List<User> list(@Param("teams") Set<Long> teams, @Param("status") int status, @Param("role") int role);

    @Insert("INSERT INTO user(name) VALUES (#{name})")
    int create(User user);
}
