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

package com.xiaomi.shepher.exception;

/**
 * Created by weichuyang on 16-7-14.
 */
public class ShepherException extends Exception {

    private int code;
    private String message;

    private ShepherException(ExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    private ShepherException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ShepherException createIllegalPathException() {
        return new ShepherException(ExceptionEnum.ILLEGALPATH_ERROR);
    }

    public static ShepherException createNoAuthorizationException() {
        return new ShepherException(ExceptionEnum.NOAUTHORIZATION_ERROR);
    }

    public static ShepherException createNoSuchRoleException() {
        return new ShepherException(ExceptionEnum.NOSUCHROLE_ERROR);
    }

    public static ShepherException createNoSuchTeamException() {
        return new ShepherException(ExceptionEnum.NOSUCHTEAM_ERROR);
    }

    public static ShepherException createNoSuchNodeException() {
        return new ShepherException(ExceptionEnum.NOSUCHNODE_ERROR);
    }

    public static ShepherException createNoSuchUserException() {
        return new ShepherException(ExceptionEnum.NOSUCHUSER_ERROR);
    }

    public static ShepherException createNoSuchUserTeamException() {
        return new ShepherException(ExceptionEnum.NOSUCHUSER_ERROR);
    }

    public static ShepherException createNoSuchReviewException() {
        return new ShepherException(ExceptionEnum.NOSUCHREVIEW_ERROR);
    }

    public static ShepherException createNoSuchSnapshotException() {
        return new ShepherException(ExceptionEnum.NOSUCHSNAPSHOT_ERROR);
    }

    public static ShepherException createNoLoginException() {
        return new ShepherException(ExceptionEnum.NOLOGIN_ERROR);
    }

    public static ShepherException createIllegalParameterException() {
        return new ShepherException(ExceptionEnum.ILLEGAL_PARAMETER_ERROR);
    }

    public static ShepherException createDBNoDataCreateException() {
        return new ShepherException(ExceptionEnum.DB_NODATA_CREATE);
    }

    public static ShepherException createDBNoDataUpdateException() {
        return new ShepherException(ExceptionEnum.DB_NODATA_UPDATE);
    }

    public static ShepherException createDBNoDataDeleteException() {
        return new ShepherException(ExceptionEnum.DB_NODATA_DELETE);
    }

    public static ShepherException createIllegalLoginTypeException() {
        return new ShepherException(ExceptionEnum.ILLEGAL_LOGINTYPE_ERROR);

    }

    public static ShepherException createUnknownException() {
        return new ShepherException(ExceptionEnum.UNKNOWN_ERROR);
    }

    public static ShepherException createUserExistsException() {
        return new ShepherException(ExceptionEnum.USEREXISTS_ERROR);
    }

    public static ShepherException createNoNodeException() {
        return new ShepherException(ExceptionEnum.NONODE_ERROR);
    }

    public static ShepherException createNodeExistsException() {
        return new ShepherException(ExceptionEnum.NODEEXISTS_ERROR);
    }

    public static ShepherException createDuplicateKeyException() {
        return new ShepherException(ExceptionEnum.DB_DUPLICATE_KEY);
    }

    public static ShepherException createDBCreateErrorException() {
        return new ShepherException(ExceptionEnum.DB_CREATE_ERROR);
    }

    public static ShepherException createDBDeleteErrorException() {
        return new ShepherException(ExceptionEnum.DB_DELETE_ERROR);
    }

    public static ShepherException createDBUpdateErrorException() {
        return new ShepherException(ExceptionEnum.DB_UPDATE_ERROR);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private enum ExceptionEnum {
        ILLEGALPATH_ERROR(1001, "Illegal path"),
        NOAUTHORIZATION_ERROR(1002, "No authorization"),
        NOSUCHROLE_ERROR(2001, "No such role"),
        NOSUCHTEAM_ERROR(2002, "No such team"),
        NOSUCHNODE_ERROR(2003, "No such node"),
        NOSUCHUSER_ERROR(2004, "No such user"),
        NOSUCHREVIEW_ERROR(2005, "No such review"),
        NOSUCHSNAPSHOT_ERROR(2006, "No such snapshot"),
        NOSUCHUSERTEAM_ERROR(2007, "No such user_team"),
        USEREXISTS_ERROR(2008, "User exists"),
        NONODE_ERROR(2009, "No such node"),
        NODEEXISTS_ERROR(2010, "Node already exists"),
        NOLOGIN_ERROR(3001, "Not login"),
        ILLEGAL_PARAMETER_ERROR(4001, "Illegal parameter"),
        DB_NODATA_CREATE(5001, "No data to create"),
        DB_NODATA_UPDATE(5002, "No data to update"),
        DB_NODATA_DELETE(5003, "No data to delete"),
        DB_CREATE_ERROR(5004, "Data create failed"),
        DB_DELETE_ERROR(5005, "Data delete failed"),
        DB_UPDATE_ERROR(5006, "Data update failed"),
        DB_DUPLICATE_KEY(5007, "Duplicate key"),
        ILLEGAL_LOGINTYPE_ERROR(6001, "Illegal login type"),
        UNKNOWN_ERROR(7001, "Internal error");

        private int code;
        private String message;

        ExceptionEnum(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}
