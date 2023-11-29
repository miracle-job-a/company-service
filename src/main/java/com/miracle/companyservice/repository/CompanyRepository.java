package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByIdAndEmailAndBno(Long id, String email, String bno);

    /**
     * @author kade
     * @param email the email
     * @return the boolean
     * 이메일로 해당 기업이 존재하는지 확인하는 메서드
     */
    Boolean existsByEmail(String email);

    /**
     * @author kade
     * @param email
     * @param password
     * 이메일과 비밀번호가 일치하는 기업 정보를 가져오는 메서드
     */
    Optional<Company> findByEmailAndPassword(String email, String password);

    /**
     * @author kade
     * @param bno
     * 사업자 번호로 기업이 존재하는지 확인하는 메서드
     */
    Boolean existsByBno(String bno);

    /**
     * @author kade
     * @param companyId
     * 기업아이디를 이용해서 해당 기업의 대표이미지를 가져오는 메서드
     */
    @Query("SELECT c.photo FROM Company c WHERE c.id = :companyId")
    String findPhotoById(Long companyId);

}