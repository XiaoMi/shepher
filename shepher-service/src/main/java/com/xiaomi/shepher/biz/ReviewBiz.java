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

package com.xiaomi.shepher.biz;

import com.xiaomi.shepher.common.Action;
import com.xiaomi.shepher.common.DaoValidator;
import com.xiaomi.shepher.common.ReviewStatus;
import com.xiaomi.shepher.dao.ReviewMapper;
import com.xiaomi.shepher.exception.ShepherException;
import com.xiaomi.shepher.model.ReviewRequest;
import com.xiaomi.shepher.util.ShepherConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by banchuanyu on 16-11-4.
 */
@Service
public class ReviewBiz {

    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewRequest create(String cluster, String path, long snapshot, long newSnapshot, ReviewStatus reviewStatus, String creator,
                                String reviewer, Action action)
            throws ShepherException {
        if (StringUtils.isBlank(cluster) || StringUtils.isBlank(path) || reviewStatus == null || action == null) {
            throw ShepherException.createIllegalParameterException();
        }
        ReviewRequest reviewRequest = new ReviewRequest(cluster, path, snapshot, newSnapshot, reviewStatus.getValue(), creator, reviewer,
                new Date(), action.getValue());
        int count;
        try {
            count = reviewMapper.create(reviewRequest);
        } catch (DuplicateKeyException e) {
            throw ShepherException.createDuplicateKeyException();
        } catch (Exception e) {
            throw ShepherException.createDBCreateErrorException();
        }

        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_CREATE);
        return reviewRequest;
    }

    public int update(long id, ReviewStatus reviewStatus, String reviewer) throws ShepherException {
        if (StringUtils.isBlank(reviewer) || reviewStatus == null) {
            throw ShepherException.createIllegalParameterException();
        }
        int count;
        try {
            count = reviewMapper.update(id, reviewStatus.getValue(), reviewer);
        } catch (Exception e) {
            throw ShepherException.createDBUpdateErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_UPDATE);
        return count;
    }

    public ReviewRequest get(long id) {
        return reviewMapper.get(id);
    }

    public int delete(long id) throws ShepherException {
        int count;
        try {
            count = reviewMapper.delete(id);
        } catch (Exception e) {
            throw ShepherException.createDBDeleteErrorException();
        }
        DaoValidator.checkSqlReturn(count, ShepherConstants.DB_OPERATE_DELETE);
        return count;
    }
}
