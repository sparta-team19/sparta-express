package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.infrastructure.client.UserClient;
import com.sparta_express.order_shipment.infrastructure.dto.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.sparta_express.order_shipment.common.exception.ErrorType.*;

@Slf4j
@Component
public class UserFallback implements UserClient {

    @Override
    public ResponseDataDto<UserResponseDto> getUser(String email) {
        log.error("Fallback triggered for getUser: userId= {}", email);
        throw new CustomException(USER_NOT_FOUND);
    }

    @Override
    public ResponseDataDto<UserResponseDto> getDeliveryManager() {
        log.error("Fallback triggered for getDeliveryManager");
        throw new CustomException(DELIVERY_MANAGER_NOT_FOUND);
    }

}
