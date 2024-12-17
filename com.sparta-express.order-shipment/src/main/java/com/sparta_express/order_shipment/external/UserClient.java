package com.sparta_express.order_shipment.external;

import com.sparta_express.order_shipment.common.ResponseDataDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/users/email/{email}")
    ResponseEntity<ResponseDataDto<UserResponseDto>> getUserByEmail(@PathVariable String email);

}
