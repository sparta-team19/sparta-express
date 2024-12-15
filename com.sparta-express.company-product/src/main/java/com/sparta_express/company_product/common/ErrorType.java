package com.sparta_express.company_product.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {

  NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;

}