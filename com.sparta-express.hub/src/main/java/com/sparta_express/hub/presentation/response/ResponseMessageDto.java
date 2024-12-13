package com.sparta_express.hub.presentation.response;

import lombok.Getter;

@Getter
public class ResponseMessageDto {
  private final int status;
  private final String message;

  public ResponseMessageDto(ResponseStatus status) {
    this.status = status.getHttpStatus().value();
    this.message = status.getMessage();
  }

  //사용 예시
  //return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.COMMENT_DELETE_SUCCESS));
}