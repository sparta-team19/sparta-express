package com.sparta_express.ai.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    //   사용예시
    // ai
    HTTP_CLIENT_ERROR(HttpStatus.BAD_REQUEST, "HTTP Client Error"),
    NOT_FOUND_AI(HttpStatus.NOT_FOUND, "AI 요청 내역이 존재하지 않습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // slack
    NOT_FOUND_SLACK(HttpStatus.NOT_FOUND, "Slack 메시지가 존재하지 않습니다."),
    DELETE_SLACK_FAIL(HttpStatus.BAD_REQUEST, "Slack 메시지 삭제 실패");


  private final HttpStatus httpStatus;
  private final String message;
}