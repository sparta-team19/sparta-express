package com.sparta_express.company_product.company.controller;

import com.sparta_express.company_product.common.ResponseDataDto;
import com.sparta_express.company_product.common.ResponseMessageDto;
import com.sparta_express.company_product.common.ResponseStatus;
import com.sparta_express.company_product.company.dto.CompanyResponse;
import com.sparta_express.company_product.company.dto.CreateCompanyRequest;
import com.sparta_express.company_product.company.dto.UpdateCompanyRequest;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    // 업체 생성
    @PostMapping
    public ResponseEntity<ResponseDataDto<CompanyResponse>> createCompany(
            @Valid @RequestBody CreateCompanyRequest createCompanyRequest) {
        Company company = companyService.createCompany(createCompanyRequest);
        CompanyResponse companyResponse = CompanyResponse.from(company);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.CREATE_SUCCESS, companyResponse));
    }

    // 업체 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDataDto<List<CompanyResponse>>> getCompanies() {
        List<CompanyResponse> companies = companyService.getCompanies().stream()
                .map(CompanyResponse::from)
                .toList();
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.LIST_SUCCESS, companies));
    }

    // 업체 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDataDto<CompanyResponse>> getCompanyById(@PathVariable UUID id) {
        Company company = companyService.getCompanyById(id);
        CompanyResponse companyResponse = CompanyResponse.from(company);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.DETAIL_SUCCESS, companyResponse));
    }

    // 업체 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDataDto<CompanyResponse>> updateCompany(
            @PathVariable UUID id,
            @RequestBody UpdateCompanyRequest request) {
        Company company = companyService.updateCompany(id, request);
        CompanyResponse companyResponse = CompanyResponse.from(company);
        return ResponseEntity.ok(new ResponseDataDto<>(ResponseStatus.UPDATE_SUCCESS, companyResponse));
    }

    // 업체 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessageDto> deleteCompany(
            @RequestHeader("X-User-Id") String email,
            @PathVariable UUID id) {
        companyService.deleteCompany(id, email);
        return ResponseEntity.ok(new ResponseMessageDto(ResponseStatus.DELETE_SUCCESS));
    }

}
