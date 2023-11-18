package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Bno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BnoRepository extends JpaRepository<Bno, Long> {

    @Query("SELECT b.status FROM Bno b WHERE b.bno = :Bno")
    Boolean findStatusByBnoIsTrue(String Bno);
    Boolean existsByBno(String Bno);
}

