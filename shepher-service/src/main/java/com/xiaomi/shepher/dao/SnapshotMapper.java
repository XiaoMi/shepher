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

import com.xiaomi.shepher.model.Snapshot;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * Created by banchuanyu on 16-11-4.
 */
@Mapper
public interface SnapshotMapper {

    @Select("SELECT id, cluster, path, content, creator, action, zk_mtime, time, status, zk_version, reviewer FROM snapshot WHERE id = #{id}")
    Snapshot get(@Param("id") long id);

    @Select("SELECT id, cluster, path, content, creator, action, zk_mtime, time, status, zk_version, reviewer " +
            "FROM snapshot WHERE path = #{path} AND cluster = #{cluster} AND status > 0 ORDER BY id DESC limit #{offset}, #{limit}")
    List<Snapshot> listByPath(@Param("path") String path, @Param("cluster") String cluster, @Param("offset") int offset, @Param("limit") int limit);

    @Select("SELECT id, cluster, path, content, creator, action, zk_mtime, time, status, zk_version, reviewer " +
            "FROM snapshot WHERE path = #{path} AND cluster = #{cluster} AND zk_mtime = #{zkMtime} AND zk_version = #{zkVersion} AND status > 0")
    Snapshot getByPathAndZk(@Param("path") String path, @Param("cluster") String cluster,
                            @Param("zkMtime") Date zkMtime, @Param("zkVersion") int zkVersion);

    @Update("UPDATE snapshot SET status = #{status}, reviewer = #{reviewer}, zk_mtime = #{zkMtime} WHERE id = #{id}")
    int update(@Param("id") long id, @Param("status") int status, @Param("reviewer") String reviewer, @Param("zkMtime") Date zkMtime);

    @Insert("INSERT INTO snapshot(cluster, path, content, creator, action, zk_mtime, status, zk_version, reviewer) VALUES " +
            "(#{cluster}, #{path}, #{content}, #{creator}, #{action}, #{zkMtime}, #{status}, #{zkVersion}, #{reviewer})")
    int create(Snapshot snapshot);

    @Delete("DELETE FROM snapshot WHERE id = #{id}")
    int delete(@Param("id") long id);
}
