package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Bno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BnoRepository extends JpaRepository<Bno, Long> {

    Boolean findStatusByBno(String Bno);
    Boolean existsByBno(String Bno);
}

