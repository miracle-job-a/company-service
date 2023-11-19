package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.CompanyFaqDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.CompanyFaq;
import com.miracle.companyservice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFaqRepository extends JpaRepository<CompanyFaq, Long> {
    /**
     * Find by company id list.
     * @param companyId the company id
     * @return the list
     * @author wjdals3936
     */
    List<CompanyFaq> findByCompanyId(Long companyId);
}
