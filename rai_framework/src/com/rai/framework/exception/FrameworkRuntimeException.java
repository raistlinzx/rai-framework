package com.rai.framework.exception;

public class FrameworkRuntimeException extends RuntimeException {

	private Exception exception;

	public FrameworkRuntimeException(String errorCode) {
		this(errorCode, null);
	}

	public FrameworkRuntimeException(String errorCode, Throwable exception) {
		super(errorCode, exception);
	}

	public Exception getRootCause() {
		if (this.exception instanceof FrameworkRuntimeException) {
			return ((FrameworkRuntimeException) this.exception).getRootCause();
		}
		return ((this.exception == null) ? this : this.exception);
	}

	public String toString() {
		if (this.exception instanceof FrameworkRuntimeException) {
			return ((FrameworkRuntimeException) this.exception).toString();
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
