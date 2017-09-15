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

package com.xiaomi.shepher.model;

import java.util.Date;

/**
 * Created by banchuanyu on 16-5-19.
 */
public class NeedReview {
    private long id;
    private String cluster;
    private String pathPrefix;
    private String reviewerList;
    private Date time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    public String getReviewerList() {
        return reviewerList;
    }

    public void setReviewerList(String reviewerList) {
        this.reviewerList = reviewerList;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "NeedReview{" +
                "id=" + id +
                ", cluster='" + cluster + '\'' +
                ", pathPrefix='" + pathPrefix + '\'' +
                ", reviewerList='" + reviewerList + '\'' +
                ", time=" + time +
                '}';
    }
}
