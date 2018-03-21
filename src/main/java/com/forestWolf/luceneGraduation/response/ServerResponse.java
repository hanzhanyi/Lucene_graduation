package com.forestWolf.luceneGraduation.response;

/**
 * 返回值  Response
 * @param <T>
 */
public class ServerResponse<T> {
    private Integer code;
    private String message;
    private T data;

    public ServerResponse() {
    }

    public ServerResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
