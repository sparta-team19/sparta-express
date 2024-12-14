package com.sparta_express.auth.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    GET_USER_SUCCESS(HttpStatus.OK, "조회에 성공했습니다.");

  private final HttpStatus httpStatus;
  private final String message;
  }