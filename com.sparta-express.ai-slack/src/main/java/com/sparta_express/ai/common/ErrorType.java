package com.sparta_express.ai.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    // ai
    HTTP_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "HTTP Client Error");


  private final HttpStatus httpStatus;
  private final String message;
}