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

import com.xiaomi.shepher.model.UserTeam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by banchuanyu on 16-11-3.
 */
@Mapper
public interface UserTeamMapper {

    @Insert("INSERT INTO user_team (user_id, team_id, role, status) VALUES (#{userId}, #{teamId}, #{roleValue}, #{statusValue})")
    int create(UserTeam userTeam);

    @Update("UPDATE user_team SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") long id, @Param("status") int status);

    @Update("UPDATE user_team SET role = #{role} WHERE id = #{id}")
    int updateRole(@Param("id") long id, @Param("role") int role);

    @Select("SELECT role FROM user_team WHERE user_id = #{user} AND team_id = #{team} AND status = #{status}")
    Integer getRoleValue(@Param("user") long user, @Param("team") long team, @Param("status") int status);

    @Select("SELECT  user_team.id AS id, user.name AS user_name, team.name AS team_name, user_team.time AS time, " +
            "role as role_value, status as status_value, team_id, user_id " +
            "FROM user_team, user, team " +
            "WHERE team_id = #{team} AND user_team.team_id = team.id AND user_team.user_id= user.id AND user_team.status = #{status}")
    List<UserTeam> listByTeam(@Param("team") long team, @Param("status") int status);

    @Select("SELECT  user_team.id AS id, user.name AS user_name, team.name AS team_name, user_team.time AS time, " +
            "role as role_value, status as status_value, team_id, user_id " +
            "FROM user_team, user, team " +
            "WHERE user_team.id = #{id} AND user_team.team_id = team.id AND user_team.user_id= user.id")
    UserTeam get(@Param("id") long id);

    @Select("SELECT  user_team.id AS id, user.name AS user_name, team.name AS team_name, user_team.time AS time, " +
            "role as role_value, status as status_value, team_id, user_id " +
            "FROM user_team, user, team " +
            "WHERE user.id= #{user} AND user_team.team_id = team.id AND user_team.user_id= user.id AND user_team.status = #{status}")
    List<UserTeam> listByUser(@Param("user") long user, @Param("status") int status);

    @Select("SELECT user.name AS user_name FROM user_team, user WHERE user_team.team_id = #{team} AND user_team.user_id = user.id")
    List<String> listUserByTeamId(@Param("team") long team);
}
