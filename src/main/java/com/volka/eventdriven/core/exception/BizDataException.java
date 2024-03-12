package com.volka.eventdriven.core.exception;

/**
 *
 * @author volka
 *
 */
public class BizDataException extends BizException {

	private static final long serialVersionUID = -6043550045833254059L;
	
	private Object[] params=null;
	private String errorCode="";

	public BizDataException(String msg) {
		super(msg);
	}

	public BizDataException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public Object[] getParams() {
		return params;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}
