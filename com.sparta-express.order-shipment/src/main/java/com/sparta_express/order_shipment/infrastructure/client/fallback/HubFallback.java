package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.infrastructure.client.HubClient;
import com.sparta_express.order_shipment.infrastructure.dto.HubResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static com.sparta_express.order_shipment.common.exception.ErrorType.HUB_ROUTES_NOT_FOUND;

@Slf4j
@Component
public class HubFallback implements HubClient {

    @Override
    public ResponseDataDto<List<HubResponseDto>> getHubRoutesInfo(UUID originHubId, UUID destinationHubId) {
        log.error("Fallback triggered for getHubRoutesInfo: originHubId= {}, destinationHubId = {}", originHubId, destinationHubId);
        throw new CustomException(HUB_ROUTES_NOT_FOUND);
    }

}
