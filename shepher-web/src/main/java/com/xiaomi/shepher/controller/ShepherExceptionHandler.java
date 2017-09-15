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

import com.xiaomi.shepher.exception.ShepherException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * The {@link ShepherExceptionHandler} is a general exception handler of this project.
 *
 * Created by weichuyang on 16-8-4.
 */
@ControllerAdvice
@Controller
public class ShepherExceptionHandler implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(com.xiaomi.shepher.controller.ShepherExceptionHandler.class);

    private static final String ERROR_PATH = "/error";

    /**
     * Handles self define {@link ShepherException}.
     *
     * @param exception
     * @param model
     * @return
     */
    @ExceptionHandler(value = ShepherException.class)
    public String handleShepherException(ShepherException exception, Model model) {
        logger.info("Handle ShepherException:code {}, message: {}", exception.getCode(), exception.getMessage());
        model.addAttribute("message", StringEscapeUtils.escapeHtml4(exception.getMessage()));
        return ERROR_PATH;
    }

    /**
     * Handles unknown exception.
     *
     * @param exception
     * @param model
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public String handleUndefinedException(Exception exception, Model model) {
        logger.warn("Handle Unknown Exception: {} ", exception.getMessage());
        model.addAttribute("message", StringEscapeUtils.escapeHtml4(exception.getMessage()));

        return ERROR_PATH;
    }

    /**
     * Handles http status error.
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request, Model model) {
        HttpStatus httpStatus = getStatus(request);
        model.addAttribute("message", httpStatus.getReasonPhrase());
        return getErrorPath();
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    /**
     * Handles error of {@link ErrorController}
     *
     * @return
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
