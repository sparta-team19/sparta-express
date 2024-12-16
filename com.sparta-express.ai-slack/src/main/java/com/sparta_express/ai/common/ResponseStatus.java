package com.sparta_express.ai.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseStatus {

    // 사용 예시
//  LOGIN_SUCCESS(HttpStatus.OK, "로그인에 성공하였습니다."),
    // ai
    CREATE_AI_SUCCESS(HttpStatus.CREATED, "AI 요청 및 응답 생성에 성공했습니다."),
    GET_AI_SUCCESS(HttpStatus.OK, "AI 요청 및 응답 조회에 성공했습니다."),
    DELETE_AI_SUCCESS(HttpStatus.OK, "AI 요청 및 응답 삭제에 성공했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}