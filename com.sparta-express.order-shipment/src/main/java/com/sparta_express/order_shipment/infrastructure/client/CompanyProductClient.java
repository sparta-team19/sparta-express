package com.sparta_express.order_shipment.infrastructure.client;

import com.sparta_express.order_shipment.application.dto.ResponseDataDto;
import com.sparta_express.order_shipment.config.FeignClientConfig;
import com.sparta_express.order_shipment.infrastructure.client.fallback.CompanyProductFallbackFactory;
import com.sparta_express.order_shipment.infrastructure.dto.CompanyResponseDto;
import com.sparta_express.order_shipment.infrastructure.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "company-product",
        configuration = FeignClientConfig.class,
        fallbackFactory = CompanyProductFallbackFactory.class)
public interface CompanyProductClient {
    /*
    * todo: 필요한 메서드
    *   1. 업체 조회    /api/companies/{companyId}
    *   2. 상품 조회    /api/products/{productId}
    *   3. 상품 재고 감소
    *   4. 상품 재고 복구
    *   반환 타입, url 확인하고 변경하기
    * */

    @GetMapping("/api/companies/{companyId}")
    ResponseDataDto<CompanyResponseDto> getCompanyById(@PathVariable UUID companyId);

    @GetMapping("/api/products/{productId}")
    ResponseDataDto<ProductResponseDto> getProductById(@PathVariable UUID productId);

    @PutMapping("/api/stocks/reduce/{productId}")
    void reduceStock(@PathVariable UUID productId, @RequestParam Integer quantity);

    @PutMapping("/api/stocks/restore/{productId}")
    void restoreStock(@PathVariable UUID productId, @RequestParam Integer quantity);

}
