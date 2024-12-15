package com.sparta_express.company_product.company.service;

import com.sparta_express.company_product.common.CustomException;
import com.sparta_express.company_product.common.ErrorType;
import com.sparta_express.company_product.company.dto.CreateCompanyRequest;
import com.sparta_express.company_product.company.dto.UpdateCompanyRequest;
import com.sparta_express.company_product.company.model.Company;
import com.sparta_express.company_product.company.repository.CompanyRepository;
import com.sparta_express.company_product.external.Hub;
import com.sparta_express.company_product.external.HubRepository;
import com.sparta_express.company_product.external.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final HubRepository hubRepository;

    // 업체 생성
    @Transactional
    public Company createCompany(CreateCompanyRequest request) {
        Hub hub = hubRepository.findById(request.getHubId())
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));

        Company company = Company.of(
                request.getName(),
                request.getAddress(),
                request.getCompanyType(),
                hub
        );

        return companyRepository.save(company);
    }

    // 업체 목록 조회
    public List<Company> getCompanies() {
        return companyRepository.findAllByIsDeleteFalse();
    }

    // 업체 상세 조회
    public Company getCompanyById(UUID id) {
        return companyRepository.findByIdAndIsDeleteFalse(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
    }

    // 업체 수정
    @Transactional
    public Company updateCompany(UUID companyId, UpdateCompanyRequest request) {
        Company company = companyRepository.findByIdAndIsDeleteFalse(companyId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        company.update(request.getName(), request.getAddress(), request.getCompanyType());
        return companyRepository.save(company);
    }

    // 업체 삭제
    @Transactional
    public void deleteCompany(UUID companyId, String email) {
        Company company = companyRepository.findByIdAndIsDeleteFalse(companyId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND));
        company.delete(email);
    }

}
