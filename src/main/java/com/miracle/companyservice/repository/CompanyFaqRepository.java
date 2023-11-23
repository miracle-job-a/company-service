package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyFaqRepository extends JpaRepository<CompanyFaq, Long> {
}
