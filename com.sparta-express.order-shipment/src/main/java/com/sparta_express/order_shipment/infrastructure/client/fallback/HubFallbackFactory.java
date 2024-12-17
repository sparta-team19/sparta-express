package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.infrastructure.client.HubClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HubFallbackFactory implements FallbackFactory<HubClient> {
    @Override
    public HubClient create(Throwable cause) {
        log.error("Error in HubClient: {}", cause.getMessage(), cause);
        return new HubFallback();
    }
}
