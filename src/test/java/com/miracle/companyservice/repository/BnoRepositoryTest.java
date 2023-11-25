package com.miracle.companyservice.repository;

import com.miracle.companyservice.entity.Bno;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class BnoRepositoryTest {

    @Autowired
    private BnoRepository bnoRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUpTest() {
        Bno bno = new Bno("111-11-11111", true);
        bnoRepository.save(bno);
    }

    @AfterEach
    void resetTest() {
        bnoRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE bno ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("사업자 상태 확인 성공")
    void findStatusByBnoIsTrue() {
        String bno = "111-11-11111";
        Boolean status = bnoRepository.findStatusByBnoIsTrue(bno);

        Assertions.assertThat(status).isTrue();
    }

    @Test
    @DisplayName("사업자 존재 확인")
    void existsByBno() {
        String bno = "111-11-11111";

        Boolean exists = bnoRepository.existsByBno(bno);

        Assertions.assertThat(exists).isTrue();
    }
}