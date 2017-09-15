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

package com.xiaomi.shepher.service;

import com.xiaomi.shepher.biz.ClusterAdminBiz;
import com.xiaomi.shepher.common.UserHolder;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by banchuanyu on 15-8-25.
 */
@Service
public class ClusterAdminService {

    private static final Logger logger = LoggerFactory.getLogger(ClusterAdminService.class);

    @Autowired
    private ClusterAdminBiz clusterAdminBiz;

    @Autowired
    private UserHolder userHolder;

    public void create(String name, String config) throws ShepherException {
        clusterAdminBiz.create(name, config);
        logger.info("Create cluster, config={}, name={}, operator={}", config, name, userHolder.getUser().getName());
    }

    public void update(String name, String config) throws ShepherException {
        clusterAdminBiz.update(name, config);
        logger.info("Update cluster, config={}, name={}, operator={}", config, name, userHolder.getUser().getName());
    }

    public void delete(String name) throws ShepherException {
        clusterAdminBiz.delete(name);
        logger.info("Delete cluster, name={}, operator={}", name, userHolder.getUser().getName());
    }

    public List<Cluster> all() {
        return clusterAdminBiz.all();
    }
}
