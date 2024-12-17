package com.sparta_express.order_shipment.infrastructure.client;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.config.FeignClientConfig;
import com.sparta_express.order_shipment.infrastructure.client.fallback.UserFallbackFactory;
import com.sparta_express.order_shipment.infrastructure.dto.DeliveryManagerResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",
        configuration = FeignClientConfig.class,
        fallbackFactory = UserFallbackFactory.class)
public interface UserClient {

    @GetMapping("/api/v1/users/{email}")
    ResponseDataDto<UserResponseDto> getUserByEmail(@PathVariable String email);

    @GetMapping("/api/v1/users/delivery/search")
    ResponseDataDto<DeliveryManagerResponseDto> getDeliveryManager();

}
