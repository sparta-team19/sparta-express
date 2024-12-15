package com.sparta_express.order_shipment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  ORDER_CREATE_SUCCESS(HttpStatus.OK, "주문 생성에 성공하였습니다."),

  SHIPMENT_UPDATE_SUCCESS(HttpStatus.OK, "배송 내역이 수정되었습니다."),
  SHIPMENT_GET_SUCCESS(HttpStatus.OK, "배송 내역 조회에 성공하였습니다."),
  SHIPMENT_DELETE_SUCCESS(HttpStatus.OK, "배송 내역이 삭제되었습니다"),
  SHIPMENT_ROUTE_UPDATE_SUCCESS(HttpStatus.OK, "배송 경로가 수정되었습니다."),
  SHIPMENT_ROUTE_GET_SUCCESS(HttpStatus.OK, "배송 경로 조회에 성공하였습니다."),
  SHIPMENT_ROUTE_DELETE_SUCCESS(HttpStatus.OK, "배송 경로가 삭제되었습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;
  }