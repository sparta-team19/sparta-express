package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.infrastructure.client.CompanyProductClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CompanyProductFallbackFactory implements FallbackFactory<CompanyProductClient> {
    @Override
    public CompanyProductClient create(Throwable cause) {
        log.error("Error in CompanyProductClient: {}", cause.getMessage(), cause);
        return new CompanyProductFallback();
    }
}
