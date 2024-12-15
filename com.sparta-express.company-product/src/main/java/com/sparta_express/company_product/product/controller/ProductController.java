package com.sparta_express.company_product.product.controller;

import com.sparta_express.company_product.common.ResponseDataDto;
import com.sparta_express.company_product.common.ResponseMessageDto;
import com.sparta_express.company_product.common.ResponseStatus;
import com.sparta_express.company_product.product.dto.CreateProductRequest;
import com.sparta_express.company_product.product.dto.ProductResponse;
import com.sparta_express.company_product.product.dto.UpdateProductRequest;
import com.sparta_express.company_product.product.model.Product;
import com.sparta_express.company_product.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<ProductResponse>> createProduct(
            @RequestBody CreateProductRequest createProductRequest) {
        Product product = productService.createProduct(createProductRequest);
        ProductResponse productResponse = ProductResponse.from(product);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_SUCCESS, productResponse));
    }

    // 상품 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<ProductResponse>>> getProducts() {
        List<ProductResponse> products = productService.getProducts().stream()
                .map(ProductResponse::from)
                .toList();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIST_SUCCESS, products));
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<ProductResponse>> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        ProductResponse productResponse = ProductResponse.from(product);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DETAIL_SUCCESS, productResponse));
    }

    // 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<ProductResponse>> updateProduct(
            @PathVariable UUID id,
            @RequestBody UpdateProductRequest request) {
        Product product = productService.updateProduct(id, request);
        ProductResponse productResponse = ProductResponse.from(product);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SUCCESS, productResponse));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

}
