package com.sparta_express.company_product.company.repository;

import com.sparta_express.company_product.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
