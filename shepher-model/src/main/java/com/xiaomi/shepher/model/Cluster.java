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
public class Cluster {
    private long id;
    // unique
    private String name;
    private String config;
    private Date time;

    public Cluster() {

    }

    public Cluster(String name, String config) {
        this.name = name;
        this.config = config;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + config.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cluster)) return false;

        final Cluster cluster = (Cluster) obj;

        if (!config.equals(cluster.config)) return false;
        if (!name.equals(cluster.name)) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", config='" + config + '\'' +
                ", time=" + time +
                '}';
    }
}
