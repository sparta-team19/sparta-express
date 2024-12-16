package com.sparta_express.order_shipment.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorType {

  COMMON_INVALID_PARAMETER( BAD_REQUEST, "잘못된 파라미터입니다."),
  COMMON_SERVER_ERROR(INTERNAL_SERVER_ERROR, "서버에서 에러가 발생하였습니다."),
  COMMON_VALIDATION_ERROR(BAD_REQUEST, "요청 데이터가 유효하지 않습니다."),

  ORDER_NOT_FOUND(NOT_FOUND, "대상을 찾을 수 없습니다."),

  USER_NOT_SAME(BAD_REQUEST, "해당 작성자가 아닙니다."),
  USER_NOT_FOUND(NOT_FOUND, "사용자가 존재하지 않습니다."),

  DELIVERY_MANAGER_NOT_FOUND(NOT_FOUND, "배송담당자가 존재하지 않습니다."),

  COMPANY_NOT_FOUND(NOT_FOUND, "업체가 존재하지 않습니다."),

  PRODUCT_NOT_FOUND(NOT_FOUND, "상품이 존재하지 않습니다."),
  PRODUCT_STOCK_DECREASE_FAILED(BAD_REQUEST, "상품 재고 감소 요청에 실패하였습니다."),
  PRODUCT_STOCK_RESTORE_FAILED(BAD_REQUEST, "상품 재고 복구 요청에 실패하였습니다."),

  HUB_ROUTES_NOT_FOUND(NOT_FOUND, "허브 경로가 존재하지 않습니다."),

  SHIPMENT_NOT_FOUND(NOT_FOUND, "배송이 존재하지 않습니다."),
  SHIPMENT_ROUTE_NOT_FOUND(NOT_FOUND, "배송 경로가 존재하지 않습니다."),
  SHIPMENT_ROUTE_(NOT_FOUND, "배송 경로가 존재하지 않습니다."),

  FEIGN_CLIENT_INVALID_REQUEST(BAD_REQUEST, "FeignClient 요청에서 잘못된 요청이 발생했습니다."),
  FEIGN_CLIENT_RESOURCE_NOT_FOUND(NOT_FOUND, "FeignClient 요청에서 리소스를 찾을 수 없습니다."),
  FEIGN_CLIENT_UNKNOWN_ERROR(INTERNAL_SERVER_ERROR, "FeignClient 요청 중 알 수 없는 에러가 발생했습니다."),

  ORDER_REQUESTER_ID_EMPTY(BAD_REQUEST, "요청자 ID가 존재하지 않습니다."),
  ORDER_RECEIVER_ID_EMPTY(BAD_REQUEST, "수신자 ID가 존재하지 않습니다."),
  ORDER_PRODUCT_ID_EMPTY(BAD_REQUEST, "상품 ID가 존재하지 않습니다."),
  ORDER_QUANTITY_EMPTY(BAD_REQUEST, "수량이 존재하지 않습니다.."),
  ORDER_QUANTITY_INVALID(BAD_REQUEST, "수량이 유효하지 않습니다."),
  ORDER_DELIVERY_ADDRESS_EMPTY(BAD_REQUEST, "배송 주소가 존재하지 않습니다."),
  ORDER_RECIPIENT_NAME_EMPTY(BAD_REQUEST, "수령인 이름이 존재하지 않습니다."),
  ORDER_DUE_DATE_EMPTY(BAD_REQUEST, "기한 날짜가 존재하지 않습니다.");

  ;

  private final HttpStatus httpStatus;
  private final String message;
}