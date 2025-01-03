package com.sparta_express.company_product.common;

import lombok.Getter;

@Getter
public class ResponseMessageDto {

  private final int status;
  private final String message;

  public ResponseMessageDto(ResponseStatus status) {
    this.status = status.getHttpStatus().value();
    this.message = status.getMessage();
  }

}