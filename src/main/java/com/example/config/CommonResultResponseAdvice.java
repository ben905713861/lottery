/**
 * @author 作者 : Ben
 * @version 创建时间：2020年12月4日 下午3:26:33
 * @description 控制器统一返回格式包装
 */

package com.example.config;

import com.example.vo.ReturnVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice("com.example.controller")
public class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
        ServerHttpResponse response) {

        if (body instanceof byte[]) {
            response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } else {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        }

        if (body == null) {
            return new ReturnVO<>();
        } else if (body instanceof Boolean) {
            var res = (Boolean)body;
            var ret = new ReturnVO<>();
            if (!res) {
                ret.setStatus(-1);
                ret.setMsg("operator on failure");
            }
            return ret;
        } else if (body instanceof byte[]) {
            return body;
        } else if (body instanceof ReturnVO) {
            return body;
        } else if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(new ReturnVO<>((String) body));
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return new ReturnVO<Object>(body);
    }

}
