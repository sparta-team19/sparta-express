package com.sparta_express.hub.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  GET_SHIPMENT_ROUTES_SUCCESS(HttpStatus.OK, "배송 허브 경로 조회에 성공하였습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
  }