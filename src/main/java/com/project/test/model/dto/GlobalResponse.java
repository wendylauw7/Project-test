package com.project.test.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

@Data
public class GlobalResponse {
  private int errorCode;
  private String errorMessage;
  @JsonInclude(Include.NON_NULL)
  private Object outputData;

  public GlobalResponse() {
  }

  public GlobalResponse(int errorCode, String errorMessage, Object object) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.outputData = object;
  }

}
