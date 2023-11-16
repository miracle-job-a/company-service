package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByEmail(String email);

    Optional<Company> findByEmailAndPassword(String email, int password);

    Boolean existsByBno(String bno);
}
