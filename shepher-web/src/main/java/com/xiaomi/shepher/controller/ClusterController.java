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

package com.xiaomi.shepher.controller;

import com.xiaomi.shepher.common.LoginRequired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The {@link ClusterController} is entrance of cluster list,
 * mapped to freemarker in directory templates/cluster.
 *
 * Created by zhangpeng on 16-11-03.
 */

@Controller
@LoginRequired
@RequestMapping({"/clusters", "/"})
public class ClusterController {

    /**
     * Lists clusters of the root path.
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list() {
        return "/cluster/list";
    }
}
