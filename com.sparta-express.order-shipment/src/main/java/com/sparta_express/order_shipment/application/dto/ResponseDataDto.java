package com.sparta_express.order_shipment.application.dto;

import lombok.Getter;

@Getter
public class ResponseDataDto<T> {
  private final ResponseStatus status;
  private final String message;
  private final T data;

  public ResponseDataDto(ResponseStatus responseStatus, T data) {
    this.status = responseStatus;
    this.message = responseStatus.getMessage();
    this.data = data;
  }

  // 사용 예시
  // return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CARD_UPDATE_SUCCESS, responseDto));
}
