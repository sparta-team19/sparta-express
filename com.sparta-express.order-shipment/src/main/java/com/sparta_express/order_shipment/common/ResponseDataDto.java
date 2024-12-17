package com.sparta_express.order_shipment.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ResponseDataDto<T> {

  private final ResponseStatus status;
  private final String message;
  private final T data;

  @JsonCreator
  public ResponseDataDto(
          @JsonProperty("status") ResponseStatus responseStatus,
          @JsonProperty("data") T data
  ) {
    this.status = responseStatus;
    this.message = responseStatus.getMessage();
    this.data = data;
  }

}
