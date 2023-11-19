package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Bno;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BnoRepositoryTest {

    @Autowired
    BnoRepository bnoRepository;

    @Test
    @DisplayName("사업자 상태 확인 성공")
    void findStatusByBnoIsTrue() {
        Bno givenBno = new Bno("111-222-33333", true);
        bnoRepository.save(givenBno);

        String bno = "111-222-33333";
        Boolean status = bnoRepository.findStatusByBnoIsTrue(bno);

        Assertions.assertThat(status).isTrue();
    }

    @Test
    @DisplayName("사업자 존재 확인")
    void existsByBno() {
        Bno givenBno = new Bno("111-222-33333", true);
        bnoRepository.save(givenBno);

        String bno = "111-222-33333";

        Boolean exists = bnoRepository.existsByBno(bno);

        Assertions.assertThat(exists).isTrue();
    }
}