package com.sparta_express.company_product.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

  COMPANY_CREATE_SUCCESS(HttpStatus.CREATED, "업체 생성이 성공적으로 완료되었습니다."),
  COMPANY_LIST_SUCCESS(HttpStatus.OK, "업체 목록 조회가 성공적으로 완료되었습니다."),
  COMPANY_DETAIL_SUCCESS(HttpStatus.OK, "업체 상세 조회가 성공적으로 완료되었습니다."),
  COMPANY_UPDATE_SUCCESS(HttpStatus.OK, "업체 수정이 성공적으로 완료되었습니다."),
  COMPANY_DELETE_SUCCESS(HttpStatus.OK, "업체 삭제가 성공적으로 완료되었습니다."),
  ;

  private final HttpStatus httpStatus;
  private final String message;

}