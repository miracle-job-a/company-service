package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.entity.Company;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("이메일 중복 확인 성공")
    void existsByEmail() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
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
        Company company = new Company(companySignUpRequestDto);

        Company result = companyRepository.save(company);
        String email = "austinTEST@oracle.com";

        Boolean exists = companyRepository.existsByEmail(email);

        Assertions.assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("회원 가입 성공")
    void save() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
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
    @DisplayName("이메일 / 비밀번호 일치 확인 성공")
    void findByEmailAndPassword() {

        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austinTEST@oracle.com")
                .password("123456!")
                .bno("999-13-14444")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();
        Company givenCompany = new Company(companySignUpRequestDto);
        Company givenResult = companyRepository.save(givenCompany);

        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austinTEST@oracle.com", "123456!");
        Optional<Company> company = companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());

        Assertions.assertThat(company.get().getEmail()).isEqualTo(companyLoginRequestDto.getEmail());
        Assertions.assertThat(company.get().getPassword()).isEqualTo(companyLoginRequestDto.getPassword().hashCode());
    }


    @Test
    @DisplayName("가입된 사업자인지 확인 성공")
    void existsByBno() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austinTEST@oracle.com")
                .password("123456!")
                .bno("111-22-33333")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();
        Company givenCompany = new Company(companySignUpRequestDto);
        Company givenResult = companyRepository.save(givenCompany);

        String bno = "111-22-33333";

        Boolean exists = companyRepository.existsByBno(bno);

        Assertions.assertThat(exists).isTrue();
    }
}