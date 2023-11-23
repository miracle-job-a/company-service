package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.CompanyFaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFaqRepository extends JpaRepository<CompanyFaq, Long> {

    List<CompanyFaq> findByCompanyId(Long companyId);
}
