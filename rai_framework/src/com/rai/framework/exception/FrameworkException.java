package com.rai.framework.exception;

public class FrameworkException extends Exception {

	private Exception exception;

	public FrameworkException(String errorCode) {
		this(errorCode, null);
	}

	public FrameworkException(String errorCode, Exception exception) {
		super(errorCode);
		this.exception = exception;
	}

	public Exception getRootCause() {
		if (this.exception instanceof FrameworkException) {
			return ((FrameworkException) this.exception).getRootCause();
		}
		return ((this.exception == null) ? this : this.exception);
	}

	public String toString() {
		if (this.exception instanceof FrameworkException) {
			return ((FrameworkException) this.exception).toString();
		}
		if (this.exception == null) {
			return super.toString();
		}
		return this.exception.toString();
	}

	public String getErrorCode() {
		return getMessage();
	}
}
