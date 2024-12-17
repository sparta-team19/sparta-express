package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.infrastructure.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserFallbackFactory implements FallbackFactory<UserClient> {
    @Override
    public UserClient create(Throwable cause) {
        log.error("Error in UserClient: {}", cause.getMessage(), cause);
        return new UserFallback();
    }
}
