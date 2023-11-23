package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.CompanyFaq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyFaqRepository extends JpaRepository<CompanyFaq, Long> {

    List<CompanyFaq> findByCompanyId(Long companyId);

    /**
     * @author kade
     * @param companyId
     * 기업아이디에 해당하는 FAQ의 개수를 반환하는 메서드
     */
    Long countByCompanyId(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @param faqId
     * 기업아이디와 FAQ아이디가 일치하는 FAQ를 찾는 메서드
     */
    boolean existsByCompanyIdAndId(Long companyId, Long faqId);
}
