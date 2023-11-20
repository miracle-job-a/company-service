package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * Exists by email boolean.
     * @param email the email
     * @return the boolean
     * @author Kade-jeon
     */
    Boolean existsByEmail(String email);

    Optional<Company> findByEmailAndPassword(String email, int password);

    Boolean existsByBno(String bno);

    @Query("SELECT c.photo FROM Company c WHERE c.id = :companyId")
    String findPhotoById(Long companyId);

    /**
     * Find company byid post common data response dto.
     * @param companyId the company id
     * @return the post common data response dto
     * @author wjdals3936
     */
    @Query("SELECT c.name, c.ceoName, c.photo, c.employeeNum, c.address, c.introduction, c.faqList " +
            "FROM Company c WHERE c.id = :companyId")
    PostCommonDataResponseDto findCompanyByid(@Param("companyId") Long companyId);



}