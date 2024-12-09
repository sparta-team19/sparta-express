package com.sparta_express.company_product.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
  }