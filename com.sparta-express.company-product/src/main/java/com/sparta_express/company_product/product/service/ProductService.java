package com.sparta_express.company_product.product.service;

import com.sparta_express.company_product.common.CustomException;
import com.sparta_express.company_product.common.ErrorType;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.repository.CompanyRepository;
import com.sparta_express.company_product.external.Hub;
import com.sparta_express.company_product.external.HubRepository;
import com.sparta_express.company_product.product.dto.CreateProductRequest;
import com.sparta_express.company_product.product.dto.UpdateProductRequest;
import com.sparta_express.company_product.product.model.Product;
import com.sparta_express.company_product.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final HubRepository hubRepository;

    // 상품 생성
    @Transactional
    public Product createProduct(CreateProductRequest request) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        Hub hub = hubRepository.findById(request.getHubId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        Product product = Product.of(request.getName(), request.getPrice(), company, hub);
        return productRepository.save(product);
    }

    // 상품 목록 조회
    public List<Product> getProducts() {
        return productRepository.findAllByIsDeleteFalse();
    }

    // 상품 상세 조회
    public Product getProductById(UUID productId) {
        return productRepository.findByIdAndIsDeleteFalse(productId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
    }

    // 상품 수정
    @Transactional
    public Product updateProduct(UUID productId, UpdateProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        product.update(request.getName(), request.getPrice());
        return product;
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(UUID productId, String email) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        product.delete(email);
    }

}
