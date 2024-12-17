package com.sparta_express.hub.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType {
    /*    사용예시
    //user
    DUPLICATE_EMAIL(HttpStatus.LOCKED, "이미 존재하는 이메일입니다."),
    DEACTIVATE_USER(HttpStatus.LOCKED, "탈퇴한 회원입니다."),
    INVALID_PASSWORD(HttpStatus.LOCKED, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(HttpStatus.LOCKED, "존재하지 않는 회원입니다."),
    NOT_AVAILABLE_PERMISSION(HttpStatus.LOCKED, "권한이 없습니다.")
     */

    //hub
    USER_ID_INVALID(HttpStatus.BAD_REQUEST, "잘못된 회원 id"),
    INVALID_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "잘못된 요청 헤더"),
    MAP_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "지도 API 오류");

  private final HttpStatus httpStatus;
  private final String message;
}