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
public class ReviewRequest {
    private long id;
    private String cluster;
    private String path;
    private long snapshot;
    private long newSnapshot;
    private int reviewStatus;
    private String creator;
    private String reviewer;
    private Date time;
    private Date updateTime;
    private int action;

    // for view, not mapping db
    private String snapshotContent;
    private String newSnapshotContent;

    public ReviewRequest() {
    }

    public ReviewRequest(String cluster, String path, long snapshot, long newSnapshot, int reviewStatus, String creator,
                         String reviewer, Date updateTime, int action) {
        this.cluster = cluster;
        this.path = path;
        this.snapshot = snapshot;
        this.newSnapshot = newSnapshot;
        this.reviewStatus = reviewStatus;
        this.creator = creator;
        this.reviewer = reviewer;
        this.updateTime = updateTime;
        this.action = action;
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

    public long getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(long snapshot) {
        this.snapshot = snapshot;
    }

    public long getNewSnapshot() {
        return newSnapshot;
    }

    public void setNewSnapshot(long newSnapshot) {
        this.newSnapshot = newSnapshot;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(int reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getSnapshotContent() {
        return snapshotContent;
    }

    public void setSnapshotContent(String snapshotContent) {
        this.snapshotContent = snapshotContent;
    }

    public String getNewSnapshotContent() {
        return newSnapshotContent;
    }

    public void setNewSnapshotContent(String newSnapshotContent) {
        this.newSnapshotContent = newSnapshotContent;
    }

    @Override
    public String toString() {
        return "ReviewRequest{" +
                "id=" + id +
                ", cluster='" + cluster + '\'' +
                ", path='" + path + '\'' +
                ", snapshot=" + snapshot +
                ", newSnapshot=" + newSnapshot +
                ", reviewStatus=" + reviewStatus +
                ", creator='" + creator + '\'' +
                ", reviewer='" + reviewer + '\'' +
                ", time=" + time +
                ", updateTime=" + updateTime +
                ", action=" + action +
                ", snapshotContent='" + snapshotContent + '\'' +
                ", newSnapshotContent='" + newSnapshotContent + '\'' +
                '}';
    }
}
