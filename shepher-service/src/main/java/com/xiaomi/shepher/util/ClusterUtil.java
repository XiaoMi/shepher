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

package com.xiaomi.shepher.util;

import com.xiaomi.shepher.model.Cluster;
import com.xiaomi.shepher.service.ClusterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by banchuanyu on 16-5-31.
 */
@Component
public class ClusterUtil {

    private static Set<String> publicClusters = new HashSet<>();
    private static List<Cluster> clusters = new CopyOnWriteArrayList<>();
    private static Map<String, String> clusterMap = new ConcurrentHashMap<>();
    private static ClusterLoadSchedule clusterLoadSchedule;

    @Autowired
    private ClusterLoadSchedule schedule;

    @PostConstruct
    private void initClusterLoadSchedule() {
        clusterLoadSchedule = schedule;
    }

    public static List<Cluster> getClusters() {
        return clusters;
    }

    public static void refreshClusters() {
        clusterLoadSchedule.loadCluster();
    }

    public static Map<String, String> getClusterMap() {
        return clusterMap;
    }

    public static boolean isPublicCluster(String cluster) {
        return publicClusters.contains(cluster);
    }

    /**
     * Cluster config load schedule.
     */
    @Component
    private static class ClusterLoadSchedule {

        @Autowired
        private ClusterAdminService clusterAdminService;

        @Scheduled(fixedRate = 5000)
        public void loadCluster() {
            List<Cluster> clustersNew = clusterAdminService.all();
            if (clustersNew.isEmpty()) {
                return;
            } else {
                // if cluster deleted or cluster config changed, delete it
                for (Cluster cluster : clusters) {
                    if (!clustersNew.contains(cluster)) {
                        clusters.remove(cluster);
                        clusterMap.remove(cluster.getName());
                    }
                }

                // if cluster added or cluster config changed, update it
                for (Cluster cluster : clustersNew) {
                    if (!clusters.contains(cluster)) {
                        clusters.add(cluster);
                        clusterMap.put(cluster.getName(), cluster.getConfig());
                    }
                }
            }
        }
    }
}


