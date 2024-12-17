package com.sparta_express.order_shipment.common.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorType errorType;

  public CustomException(ErrorType errorType) {
    super(errorType.getMessage());
    this.errorType = errorType;
  }

  public CustomException(ErrorType errorType, Throwable cause) {
    super(errorType.getMessage(), cause);
    this.errorType = errorType;
  }

  public CustomException(ErrorType errorType, String additionalMessage) {
    super(errorType.getMessage() + additionalMessage);
    this.errorType = errorType;
  }

}
