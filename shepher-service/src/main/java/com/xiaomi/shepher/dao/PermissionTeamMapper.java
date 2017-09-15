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
import com.xiaomi.shepher.model.PermissionTeam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by banchuanyu on 16-11-3.
 */
@Mapper
public interface PermissionTeamMapper {
    @Select("SELECT permission_team.id AS id, permission_id, team_id, status AS status_value, permission_team.time AS time, " +
            "permission.cluster AS cluster, permission.path AS path, team.name AS team_name FROM permission_team, permission, team " +
            "WHERE permission_team.permission_id = permission.id AND permission_team.team_id = team.id " +
            "AND team.id = #{team} AND cluster = #{cluster} AND path = #{path} AND status = #{status}")
    PermissionTeam get(@Param("team") long team, @Param("cluster") String cluster, @Param("path") String path, @Param("status") int status);

    @Select("SELECT permission_team.id AS id, permission_id, team_id, status AS status_value, permission_team.time AS time, " +
            "permission.cluster AS cluster, permission.path AS path, team.name AS team_name " +
            "FROM permission_team, permission, team " +
            "WHERE permission_team.permission_id = permission.id AND permission_team.team_id = team.id AND status = #{status} ORDER BY team_id DESC")
    List<PermissionTeam> list(@Param("status") int status);

    @Select("SELECT permission_team.id AS id, permission_id, team_id, status AS status_value, permission_team.time AS time, " +
            "permission.cluster AS cluster, permission.path AS path, team.name AS team_name " +
            "FROM permission_team, permission, team " +
            "WHERE permission_team.permission_id = permission.id AND permission_team.team_id = team.id " +
            "AND cluster = #{cluster} AND path = #{path} AND status = #{status}")
    List<PermissionTeam> listByPath(@Param("cluster") String cluster, @Param("path") String path, @Param("status") int status);

    @Select("SELECT permission_team.id AS id, permission_id, team_id, status AS status_value, permission_team.time AS time, " +
            "permission.cluster AS cluster, permission.path AS path, team.name AS team_name " +
            "FROM permission_team, permission, team " +
            "WHERE permission_team.permission_id = permission.id AND permission_team.team_id = team.id " +
            "AND cluster = #{cluster} AND path IN (#{paths}) AND status = #{status}")
    @Lang(MybatisExtendedLanguageDriver.class)
    List<PermissionTeam> listByPaths(@Param("cluster") String cluster, @Param("paths") List<String> paths, @Param("status") int status);

    @Select("SELECT permission_team.id AS id, permission_id, team_id, status AS status_value, permission_team.time AS time, " +
            "permission.cluster AS cluster, permission.path AS path,team.name AS team_name " +
            "FROM permission_team, permission, team " +
            "WHERE team.id = #{team} AND permission_team.permission_id = permission.id AND permission_team.team_id = team.id  AND status = #{status}")
    List<PermissionTeam> listByTeam(@Param("team") long team, @Param("status") int status);

    @Update("UPDATE permission_team SET status = #{status} WHERE id = #{id}")
    int update(@Param("id") long id, @Param("status") int status);

    @Insert("INSERT INTO permission_team (permission_id, team_id, status) VALUES (#{permissionId}, #{teamId}, #{statusValue})")
    int create(PermissionTeam permissionTeam);
}
