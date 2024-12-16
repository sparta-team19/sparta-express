package com.sparta_express.auth.common;

import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
  private ResponseStatus status;
  private String message;
  private T data;

  public ResponseDataDto(ResponseStatus responseStatus, T data) {
    this.status = responseStatus;
    this.message = responseStatus.getMessage();
    this.data = data;
  }

  // 사용 예시
  // return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CARD_UPDATE_SUCCESS, responseDto));
}
