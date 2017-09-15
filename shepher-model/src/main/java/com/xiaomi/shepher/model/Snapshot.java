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

import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.ReviewStatus;

import java.util.Date;

/**
 * Created by banchuanyu on 16-5-19.
 */
public class Snapshot {
    private long id;
    private String cluster;
    private String path;
    private String content;
    private String creator;
    private int action;
    private Date time;
    private Date zkMtime;
    private int status;
    private int zkVersion;
    private String reviewer;

    // not mapped to database
    private Action actionDetail;
    private ReviewStatus reviewStatus;

    public Snapshot() {
    }

    public Snapshot(String cluster, String path, String content, String creator, int action, Date zkMtime, int status, int zkVersion,
                    String reviewer) {
        this.cluster = cluster;
        this.path = path;
        this.content = content;
        this.creator = creator;
        this.action = action;
        this.zkMtime = zkMtime;
        this.status = status;
        this.zkVersion = zkVersion;
        this.reviewer = reviewer;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getZkMtime() {
        return zkMtime;
    }

    public void setZkMtime(Date zkMtime) {
        this.zkMtime = zkMtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getZkVersion() {
        return zkVersion;
    }

    public void setZkVersion(int zkVersion) {
        this.zkVersion = zkVersion;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Action getActionDetail() {
        if (actionDetail == null) {
            actionDetail = Action.get(this.action);
        }
        return actionDetail;
    }

    public void setActionDetail(Action actionDetail) {
        this.actionDetail = actionDetail;
    }

    public ReviewStatus getReviewStatus() {
        if (reviewStatus == null) {
            reviewStatus = ReviewStatus.get(this.status);
        }
        return reviewStatus;
    }

    public void setReviewStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "id=" + id +
                ", cluster='" + cluster + '\'' +
                ", path='" + path + '\'' +
                ", content='" + content + '\'' +
                ", creator='" + creator + '\'' +
                ", action=" + action +
                ", time=" + time +
                ", zkMtime=" + zkMtime +
                ", status=" + status +
                ", zkVersion=" + zkVersion +
                ", reviewer='" + reviewer + '\'' +
                ", actionDetail=" + actionDetail +
                ", reviewStatus=" + reviewStatus +
                '}';
    }
}
