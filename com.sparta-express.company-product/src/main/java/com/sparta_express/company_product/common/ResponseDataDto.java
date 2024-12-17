package com.sparta_express.company_product.common;

import lombok.Getter;

@Getter
public class ResponseDataDto<T> {

  private final ResponseStatus status;
  private final String message;
  private final T data;

  public ResponseDataDto(ResponseStatus responseStatus, T data) {
    this.status = responseStatus;
    this.message = responseStatus.getMessage();
    this.data = data;
  }

}
