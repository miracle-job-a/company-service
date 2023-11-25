package com.miracle.companyservice.repository;


import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.entity.Company;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void setUpTest() {
        companyRepository.deleteAll();
        entityManager.createNativeQuery("ALTER TABLE Company ALTER COLUMN id RESTART WITH 1").executeUpdate();
        entityManager.flush();
        entityManager.clear();

        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austinTEST@oracle.com")
                .password("123456!")
                .bno("999-99-33333")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();
        Company givenCompany = new Company(companySignUpRequestDto);
        Company save = companyRepository.save(givenCompany);
        System.out.println(save.getId());
    }

    @Test
    @DisplayName("[인터셉터] 회원 인증")
    void existsByIdAndEmailAndBno() {
        long id = 1L;
        String email = "austinTEST@oracle.com";
        String bno = "999-99-33333";

        Boolean exists = companyRepository.existsByIdAndEmailAndBno(id, email, bno);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("이메일 중복 확인")
    void existsByEmail() {
        String email = "austinTEST@oracle.com";

        Boolean exists = companyRepository.existsByEmail(email);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("회원 가입")
    void save() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password("password")
                .bno("111-11-11111")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();
        Company company = new Company(companySignUpRequestDto);

        Company result = companyRepository.save(company);

        assertThat(result.getEmail()).isEqualTo(company.getEmail());
        assertThat(result.getName()).isEqualTo(company.getName());
        assertThat(result.getPassword()).isEqualTo(company.getPassword());
        assertThat(result.getBno()).isEqualTo(company.getBno());
        assertThat(result.getCeoName()).isEqualTo(company.getCeoName());
        assertThat(result.getAddress()).isEqualTo(company.getAddress());
        assertThat(result.getEmployeeNum()).isEqualTo(company.getEmployeeNum());
        assertThat(result.getSector()).isEqualTo(company.getSector());
        assertThat(result.getPhoto()).isEqualTo(company.getPhoto());
        assertThat(result.getIntroduction()).isEqualTo(company.getIntroduction());
    }

    @Test
    @DisplayName("이메일 / 비밀번호 일치 확인")
    void findByEmailAndPassword() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austinTEST@oracle.com", "123456!");
        Optional<Company> company = companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());

        Assertions.assertThat(company.get().getEmail()).isEqualTo(companyLoginRequestDto.getEmail());
        Assertions.assertThat(company.get().getPassword()).isEqualTo(companyLoginRequestDto.getPassword().hashCode());
    }

    @Test
    @DisplayName("가입된 사업자인지 확인")
    void existsByBno() {
        String bno = "999-99-33333";

        Boolean exists = companyRepository.existsByBno(bno);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("기업아이디로 해당 이미지 가져오기")
    void findPhotoById() {
        Optional<Company> company = companyRepository.findById(1L);

        String photo = companyRepository.findPhotoById(company.get().getId());

        Assertions.assertThat(photo).isEqualTo(company.get().getPhoto());
    }

    @Test
    @DisplayName("기업아이디로 기업 찾기")
    void findById() {
        Long givenId = 1L;

        Optional<Company> company = companyRepository.findById(givenId);

        Assertions.assertThat(company.get().getId()).isEqualTo(givenId);
    }
}