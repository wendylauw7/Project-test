package com.project.test.model.exception;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorMessage {
	  private int errorCode;
	  private String errorMessage;
	  private String timestamp;
	  private String description;
	  
	  public ErrorMessage(int errorCode, String errorMessage, Date timestamp, String description) {
		  	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		  	String string  = dateFormat.format(timestamp);
		    this.errorCode = errorCode;
		    this.errorMessage = errorMessage;
		    this.timestamp = string;
		    this.description = description;
		  }
		  public int getErrorCode() {
		    return errorCode;
		  }
		  public String getTimestamp() {
		    return timestamp;
		  }
		  public String getErrorMessage() {
		    return errorMessage;
		  }
		  public String getDescription() {
		    return description;
		  }
}
