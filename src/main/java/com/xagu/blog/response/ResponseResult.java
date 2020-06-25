package com.xagu.blog.response;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 统一封装返回类型
 */
public class ResponseResult {
    private boolean success;
    private Integer code;
    private String message;
    private Object data;

    public static ResponseResult state(ResponseState responseState) {
        return new ResponseResult(responseState);
    }

    public static ResponseResult decide(boolean isSuccess, String successMsg, String failMessage) {
        if (isSuccess) {
            return ResponseResult.SUCCESS(successMsg);
        } else {
            return ResponseResult.FAILED(failMessage);
        }
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(ResponseState.SUCCESS);
    }

    public static ResponseResult SUCCESS(String message) {
        ResponseResult responseResult = new ResponseResult(ResponseState.SUCCESS);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult FAILED() {
        return new ResponseResult(ResponseState.FAILED);
    }

    public static ResponseResult FAILED(String message) {
        ResponseResult responseResult = new ResponseResult(ResponseState.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }

    public ResponseResult(ResponseState responseState) {
        this.success = responseState.isSuccess();
        this.code = responseState.getCode();
        this.message = responseState.getMessage();
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
