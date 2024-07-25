package com.project.test.model.exception;

@SuppressWarnings("serial")
public class CustomException extends RuntimeException {
	private final int     errorCode;
	  private final String     errorMessage;

	  public CustomException(int errorCode, String errorMessage) {
	    this.errorCode = errorCode;
	    this.errorMessage = errorMessage;
	  }

	  public CustomException(String errorCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int errorCode1, String errorMessage) {
	    super(errorCode, cause, enableSuppression, writableStackTrace);
	    this.errorCode = errorCode1;
	    this.errorMessage = errorMessage;
	  }

	  public int getErrorCode() {
	    return errorCode;
	  }

	  public String getErrorMessage() {
	    return errorMessage;
	  }
}
