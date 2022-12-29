package com.example.book.util;

import java.io.Serializable;

public class CommonResponse implements Serializable {
    private int code;
    private String message;
    private Object data;

    public static CommonResponse success(Object data) {
        CommonResponse response = new CommonResponse();
        response.code = 200;
        response.message = "ok";
        response.data = data;

        return response;
    }

    public static CommonResponse success(String message, Object data) {
        CommonResponse response = new CommonResponse();
        response.code = 200;
        response.message = message;
        response.data = data;

        return response;
    }

    public static CommonResponse fail(int code, String message) {
        CommonResponse response = new CommonResponse();
        response.code = code;
        response.message = message;
        response.data = null;

        return response;
    }

    public static CommonResponse forbidden() {
        CommonResponse response = new CommonResponse();
        response.data = null;
        response.code = 403;
        response.message = "权限不允许";

        return response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
