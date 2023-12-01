package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.response.CompanyListForAdminResponseDto;
import com.miracle.companyservice.dto.response.CompanyListResponseDto;
import com.miracle.companyservice.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
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


    @Query("SELECT c " +
            "FROM Company c " +
            "WHERE c.createdAt >= :date")
    Page<Company> findAllByCreatedAt(LocalDateTime date, Pageable pageable);


    /**
     * @author wjdals3936
     * @param keyword
     * @param pageable
     * @return Page<CompanyListResponseDto>
     * 사용자가 키워드를 검색하면 해당 키워드가 포함된 기업명 조회 후, 데이터를 반환하는 메서드
     */
    @Query("SELECT c FROM Company c WHERE " +
            "c.name LIKE :keyword " +
            "ORDER BY c.createdAt DESC")
    Page<CompanyListResponseDto> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}