package com.miracle.companyservice.repository;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.entity.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("회원 가입 성공")
    void save() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austinTEST@oracle.com")
                .password("password")
                .bno("999-13-14444")
                .ceoName("오스틴 강")
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
}