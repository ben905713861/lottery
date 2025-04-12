/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description
 */

package com.example.vo;

import lombok.Data;

@Data
public class ReturnVO<T> {

    private Integer status;
    private String msg;
    private T data;

    public ReturnVO() {
        this.status = 0;
        this.msg = "ok";
    }

    public ReturnVO(T data) {
        this();
        this.data = data;
    }

    public static ReturnVO<Object> success() {
        return new ReturnVO<>("成功");
    }

    public static ReturnVO<Object> fail(String msg) {
        var ret = new ReturnVO<Object>();
        ret.setStatus(-1);
        ret.setMsg(msg);
        return ret;
    }

    public static ReturnVO<Object> init(int status, String msg) {
        var ret = new ReturnVO<Object>();
        ret.setStatus(status);
        ret.setMsg(msg);
        return ret;
    }

    public static ReturnVO<Object> init(int status, String msg, Object data) {
        var ret = new ReturnVO<Object>();
        ret.setStatus(status);
        ret.setMsg(msg);
        ret.setData(data);
        return ret;
    }

}
