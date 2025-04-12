/**
 * @author 作者: Ben
 * @version 创建时间: 2020年12月5日
 * @description
 */

package com.example.exception;

public class RestException extends RuntimeException {

    private static final long serialVersionUID = 3932629662758110417L;
    private int status = -1;

    public RestException(String msg) {
        super(msg);
    }

    public RestException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
