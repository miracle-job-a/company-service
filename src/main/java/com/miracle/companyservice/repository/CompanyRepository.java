package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    //Optional<Company> findByEmail(String email);
    Boolean existsByEmail(String email);
}
