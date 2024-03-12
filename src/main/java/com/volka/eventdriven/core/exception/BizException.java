package com.volka.eventdriven.core.exception;


import com.volka.eventdriven.core.constant.ResultCode;

/**
 * 런타임 비즈니스 예외 처리
 *
 * @author volka
 */
public class BizException extends RuntimeException {

    private String errorCode = "";
    private String systemMessage="";

    public BizException(String msg) {
        super(msg);
        this.errorCode = msg;
    }

    public BizException(Throwable cause){ super(cause); }

    public BizException(String msg, Throwable cause) { super(msg, cause); }

    public BizException(String msg, String systemMessage){
        super(msg);
        this.systemMessage = systemMessage;
    }

    public BizException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.errorCode = resultCode.getCode();
        this.systemMessage = resultCode.getMessage();
    }

    public BizException(ResultCode resultCode, Throwable e){
        super(e);
        this.errorCode = resultCode.getCode();
        this.systemMessage = resultCode.getMessage();
    }

    public String getErrorCode() { return errorCode; }

    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getSystemMessage() { return systemMessage; }

    public void setSystemMessage(String systemMessage) { this.systemMessage = systemMessage; }
}
