package com.sparta_express.auth.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    //user
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이메일이 이미 사용 중입니다."),
    INVALID_EMAIL_OR_PASSWORD(HttpStatus.LOCKED, "이메일 또는 비밀번호가 잘못되었습니다."),
    NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다.")

  ;

  private final HttpStatus httpStatus;
  private final String message;
}