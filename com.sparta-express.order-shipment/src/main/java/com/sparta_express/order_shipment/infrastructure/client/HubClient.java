package com.sparta_express.order_shipment.infrastructure.client;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.config.HubClientConfig;
import com.sparta_express.order_shipment.infrastructure.client.fallback.HubFallbackFactory;
import com.sparta_express.order_shipment.infrastructure.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "hub",
        configuration = HubClientConfig.class,
        fallbackFactory = HubFallbackFactory.class)
public interface HubClient {

    @GetMapping("/api/interhub-routes/shipment-interhub-routes")
    ResponseDataDto<List<HubResponseDto>> getHubRoutesInfo(@RequestParam UUID originHubId,
                                                           @RequestParam UUID destinationHubId);
}
