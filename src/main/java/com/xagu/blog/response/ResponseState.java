package com.xagu.blog.response;

/**
 * @author xagu
 * Created on 2020/6/22
 * Email:xagu_qc@foxmail.com
 * Describe: 返回状态
 */
public enum ResponseState {
    /**
     * 403
     */
    ERROR_403(false, 40003, "权限不足"),
    ERROR_404(false, 40004, "页面丢失"),
    ERROR_504(false, 50004, "系统繁忙，请稍后重试"),
    ERROR_505(false, 50005, "请求错误，请检查提交的数据"),
    /**
     * 成功
     */
    SUCCESS(true,20000,"操作成功"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(true,20001,"登录成功"),

    /**
     * 注册成功
     */
    REGISTER_SUCCESS(true,20001,"注册成功"),

    /**
     * 失败
     */
    FAILED(false,40000,"操作失败"),

    /**
     * 获取资源失败
     */
    GET_RES_FAILED(false,40001,"获取资源失败"),

    /**
     * 未登录
     */
    ACCOUNT_NO_LOGIN(false,400002,"账号未登录"),

    /**
     * 未登录
     */
    ASSESS_DENIED(false,400003,"无权限访问"),

    /**
     * 用户已冻结
     */
    ACCOUNT_DENIED(false,400004,"用户已冻结！"),

    /**
     * 登录失败
     */
    LOGIN_FAILED(false,49999,"登录失败");

    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;

    ResponseState(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
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
}
