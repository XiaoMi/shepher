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

import com.xiaomi.shepher.model.ReviewRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * Created by banchuanyu on 16-11-4.
 */
@Mapper
public interface ReviewMapper {
    @Select("SELECT review.id, review.cluster, review.path, review.snapshot, review.new_snapshot, review.review_status, " +
            "review.creator, review.reviewer, review.time, review.update_time, review.action, old_snapshot.content AS snapshot_content, " +
            "new_snapshot.content AS new_snapshot_content " +
            "FROM review_request AS review, snapshot AS old_snapshot, snapshot AS new_snapshot " +
            "WHERE review.id = #{id} AND review.snapshot = old_snapshot.id AND review.new_snapshot = new_snapshot.id")
    ReviewRequest get(@Param("id") long id);

    @Insert("INSERT INTO review_request(cluster, path, snapshot, new_snapshot, review_status, creator, reviewer, update_time, action) " +
            "VALUES (#{cluster}, #{path}, #{snapshot}, #{newSnapshot}, #{reviewStatus}, #{creator}, #{reviewer}, #{updateTime}, #{action})")
    int create(ReviewRequest reviewRequest);

    @Update("UPDATE review_request SET review_status = #{reviewStatus}, update_time = now(), reviewer = #{reviewer} WHERE id = #{id}")
    int update(@Param("id") long id, @Param("reviewStatus") int reviewStatus, @Param("reviewer") String reviewer);

    @Delete("DELETE FROM review_request WHERE id = #{id}")
    int delete(@Param("id") long id);
}
