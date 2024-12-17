package com.sparta_express.company_product.common;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  public static final String ERROR = "ERROR";
  private final String result;
  private final ErrorType errorType;

  public CustomException(ErrorType errorType) {
    this.result = ERROR;
    this.errorType = errorType;
  }

}
