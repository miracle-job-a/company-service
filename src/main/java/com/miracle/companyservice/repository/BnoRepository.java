package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Bno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BnoRepository extends JpaRepository<Bno, Long> {

    /**
     * @author kade
     * @param Bno
     * @return Boolean
     * 사업자 번호 상태가 정상인지 만료인지 반환하는 메서드
     */
    @Query("SELECT b.status FROM Bno b WHERE b.bno = :Bno")
    Boolean findStatusByBnoIsTrue(String Bno);

    /**
     * @author kade
     * @param Bno
     * @return Booelan
     * 등록된 사업자인지 아닌지 반환하는 메서드
     */
    Boolean existsByBno(String Bno);
}

