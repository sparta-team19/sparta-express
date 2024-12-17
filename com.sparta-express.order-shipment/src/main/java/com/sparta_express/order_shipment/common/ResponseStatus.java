package com.sparta_express.order_shipment.common;

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
  ;

  private final HttpStatus httpStatus;
  private final String message;

}