package com.sparta_express.order_shipment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  CREATE_SUCCESS(HttpStatus.CREATED, "생성이 성공적으로 완료되었습니다."),
  LIST_SUCCESS(HttpStatus.OK, "목록 조회가 성공적으로 완료되었습니다."),
  DETAIL_SUCCESS(HttpStatus.OK, "상세 조회가 성공적으로 완료되었습니다."),
  UPDATE_SUCCESS(HttpStatus.OK, "수정이 성공적으로 완료되었습니다."),
  DELETE_SUCCESS(HttpStatus.OK, "삭제가 성공적으로 완료되었습니다."),
  VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 오류입니다."),

  ORDER_CREATE_SUCCESS(HttpStatus.OK, "주문 생성에 성공하였습니다."),

  SHIPMENT_UPDATE_SUCCESS(HttpStatus.OK, "배송 내역이 수정되었습니다."),
  SHIPMENT_GET_SUCCESS(HttpStatus.OK, "배송 내역 조회에 성공하였습니다."),
  SHIPMENT_DELETE_SUCCESS(HttpStatus.OK, "배송 내역이 삭제되었습니다"),
  SHIPMENT_ROUTE_UPDATE_SUCCESS(HttpStatus.OK, "배송 경로가 수정되었습니다."),
  SHIPMENT_ROUTE_GET_SUCCESS(HttpStatus.OK, "배송 경로 조회에 성공하였습니다."),
  SHIPMENT_ROUTE_DELETE_SUCCESS(HttpStatus.OK, "배송 경로가 삭제되었습니다."),

  GET_SHIPMENT_ROUTES_SUCCESS(HttpStatus.OK, "배송 경로 조회에 성공하였습니다."),

  SIGNUP_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
  LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
  GET_USER_SUCCESS(HttpStatus.OK, "유저 조회에 성공했습니다."),
  UPDATE_USER_SUCCESS(HttpStatus.OK, "유저 정보 수정에 성공했습니다."),
  DELETE_USER_SUCCESS(HttpStatus.OK, "회원 탈퇴에 성공했습니다."),
  SEARCH_USER_SUCCESS(HttpStatus.OK, "회원 검색에 성공했습니다."),

  CREATE_DELIVERY_MANAGER_SUCCESS(HttpStatus.CREATED, "배송 담당자 등록에 성공했습니다."),
  GET_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 조회에 성공했습니다."),
  PUT_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 수정에 성공했습니다."),
  DELETE_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 삭제에 성공했습니다."),
  SEARCH_DELIVERY_MANAGER_SUCCESS(HttpStatus.OK, "배송 담당자 검색에 성공했습니다.");

  ;

  private final HttpStatus httpStatus;
  private final String message;
  }