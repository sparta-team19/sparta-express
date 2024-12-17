package com.sparta_express.order_shipment.infrastructure.client.fallback;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.common.exception.CustomException;
import com.sparta_express.order_shipment.infrastructure.client.CompanyProductClient;
import com.sparta_express.order_shipment.infrastructure.dto.CompanyResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.ProductResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.sparta_express.order_shipment.common.exception.ErrorType.*;


@Slf4j
@Component
public class CompanyProductFallback implements CompanyProductClient {

    @Override
    public ResponseDataDto<CompanyResponseDto> getCompanyById(UUID companyId) {
        log.error("Fallback triggered for getCompany: companyId= {}", companyId);
        throw new CustomException(COMPANY_NOT_FOUND);
    }

    @Override
    public ResponseDataDto<ProductResponseDto> getProductById(UUID productId) {
        log.error("Fallback triggered for getProduct: productId= {}", productId);
        throw new CustomException(PRODUCT_NOT_FOUND);
    }

    @Override
    public void reduceStock(UUID productId, Integer quantity) {
        log.error("Fallback triggered for decreaseStock: productId= {}, quantity= {}", productId, quantity);
        throw new CustomException(PRODUCT_STOCK_DECREASE_FAILED);
    }

    @Override
    public void restoreStock(UUID productId, Integer quantity) {
        log.error("Fallback triggered for restoreProductStock: productId= {}, quantity= {}", productId, quantity);
        throw new CustomException(PRODUCT_STOCK_RESTORE_FAILED);
    }

}
