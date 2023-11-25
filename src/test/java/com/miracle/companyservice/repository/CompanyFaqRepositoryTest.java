package com.miracle.companyservice.repository;


import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.CompanyFaq;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CompanyFaqRepositoryTest {

    @Autowired
    private CompanyFaqRepository companyFaqRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUpTest() {
       Company company = Company.builder()
                .name("오라클코리아")
                .email("austinTEST@oracle.com")
                .password("password")
                .bno("999-13-14444")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();
        companyRepository.save(company);
    }

    @AfterEach
    void resetTest() {
        companyFaqRepository.deleteAll();
        companyRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE Company ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE Company_faq ALTER COLUMN id RESTART WITH 1").executeUpdate();

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("FAQ 저장 테스트")
    void save() {
        Optional<Company> company = companyRepository.findById(1L);

        CompanyFaq companyFaq1 = new CompanyFaq("질문1", "답변1", company.get());
        CompanyFaq companyFaq2 = new CompanyFaq("질문2", "답변2", company.get());
        CompanyFaq save1 = companyFaqRepository.save(companyFaq1);
        CompanyFaq save2 = companyFaqRepository.save(companyFaq2);

        List<CompanyFaq> byCompanyId = companyFaqRepository.findByCompanyId(1L);

        Assertions.assertThat(save1.getId()).isEqualTo(byCompanyId.get(0).getId());
        Assertions.assertThat(save1.getCompany()).isEqualTo(byCompanyId.get(0).getCompany());
        Assertions.assertThat(save1.getQuestion()).isEqualTo(byCompanyId.get(0).getQuestion());
        Assertions.assertThat(save1.getAnswer()).isEqualTo(byCompanyId.get(0).getAnswer());
        Assertions.assertThat(save2.getId()).isEqualTo(byCompanyId.get(1).getId());
        Assertions.assertThat(save2.getCompany()).isEqualTo(byCompanyId.get(1).getCompany());
        Assertions.assertThat(save2.getQuestion()).isEqualTo(byCompanyId.get(1).getQuestion());
        Assertions.assertThat(save2.getAnswer()).isEqualTo(byCompanyId.get(1).getAnswer());

    }

    @Test
    @DisplayName("기업에 해당하는 FAQ 개수 반환")
    void countByCompanyId() {
        Optional<Company> company = companyRepository.findById(1L);

        CompanyFaq companyFaq1 = new CompanyFaq("질문1", "답변1", company.get());
        CompanyFaq companyFaq2 = new CompanyFaq("질문2", "답변2", company.get());
        companyFaqRepository.save(companyFaq1);
        companyFaqRepository.save(companyFaq2);

        Long count = companyFaqRepository.countByCompanyId(1L);

        Assertions.assertThat(count).isEqualTo(2L);
    }

    @Test
    @DisplayName("기업아이디와 FAQ의 기업아이디 일치여부 확인")
    void existsByCompanyIdAndId() {
        Optional<Company> company = companyRepository.findById(1L);

        CompanyFaq companyFaq1 = new CompanyFaq("질문1", "답변1", company.get());
        CompanyFaq companyFaq2 = new CompanyFaq("질문2", "답변2", company.get());
        companyFaqRepository.save(companyFaq1);
        companyFaqRepository.save(companyFaq2);

        boolean result1 = companyFaqRepository.existsByCompanyIdAndId(1L, 1L);
        boolean result2 = companyFaqRepository.existsByCompanyIdAndId(1L, 2L);

        Assertions.assertThat(result1).isTrue();
        Assertions.assertThat(result2).isTrue();
    }

    @Test
    @DisplayName("FAQ 삭제")
    void delete() {
        Optional<Company> company = companyRepository.findById(1L);
        CompanyFaq companyFaq1 = new CompanyFaq("질문1", "답변1", company.get());
        CompanyFaq companyFaq2 = new CompanyFaq("질문2", "답변2", company.get());
        CompanyFaq save1 = companyFaqRepository.save(companyFaq1);
        CompanyFaq save2 = companyFaqRepository.save(companyFaq2);
        Long id1 = save1.getId();
        Long id2 = save2.getId();

        companyFaqRepository.deleteById(id1);
        Long count = companyFaqRepository.countByCompanyId(1L);

        Assertions.assertThat(count).isEqualTo(1L);
    }
}