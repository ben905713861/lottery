/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description
 */

package com.example.config;

import com.example.exception.RestException;
import com.example.vo.ReturnVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice("com.wuxb.leaga.controller")
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数错误
     *
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class, MethodArgumentTypeMismatchException.class,
        HttpMessageNotReadableException.class, HttpMediaTypeNotSupportedException.class, IllegalStateException.class})
    public ReturnVO<Object> paramErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        response.setStatus(400);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        return ReturnVO.init(400, e.getMessage());
    }

    /**
     * 参数验证不通过
     *
     * @return
     */
    @ExceptionHandler({BindException.class, ConstraintViolationException.class})
    public ReturnVO<Object> paramNotAllowedHandler(HttpServletRequest request, HttpServletResponse response,
        Exception e) {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        if (e instanceof BindException) {
            var fieldError = ((BindException)e).getBindingResult().getFieldError();
            var msg = "[" + fieldError.getField() + "]" + fieldError.getDefaultMessage();
            return ReturnVO.init(-1, msg);
        }
        var msg = ((ConstraintViolationException)e).getMessage();
        return ReturnVO.init(-1, msg);
    }

    /**
     * 是约定的常规错误返回
     *
     * @return
     */
    @ExceptionHandler(RestException.class)
    public ReturnVO<Object> restException(HttpServletRequest request, HttpServletResponse response,
        final RestException e) {
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        return ReturnVO.init(e.getStatus(), e.getMessage());
    }

    /**
     * 是未知的错误
     *
     * @return
     */
    @ExceptionHandler
    public ReturnVO<Object> exception(HttpServletRequest request, HttpServletResponse response, final Exception e) {
        response.setStatus(500);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        logger.error(request.getMethod() + " " + request.getRequestURI() + "?" + request.getQueryString(), e);
        return ReturnVO.init(500, e.getMessage());
    }

}
