package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.api.BaseApi;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.repository.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@SpringBootTest
class CompanyServiceImplTest {

    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUpTest() {
        companyService = new CompanyServiceImpl(companyRepository);
    }

    @Test
    @DisplayName("기업 회원가입 성공 테스트 - 서비스")
    void signUpCompany() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password(1234)
                .bno("111-13-14444")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();

        Company company = new Company(companySignUpRequestDto);

        BaseApi result = BaseApi.builder()
                .httpStatus(HttpStatus.OK)
                .code("200_1")
                .message("회원가입 성공")
                .build();

        Mockito.when(companyRepository.save(company)).thenReturn(company);
        BaseApi baseApi = companyService.signUpCompany(companySignUpRequestDto);

        Assertions.assertThat(baseApi.getHttpStatus()).isEqualTo(result.getHttpStatus());
        Assertions.assertThat(baseApi.getCode()).isEqualTo(result.getCode());
        Assertions.assertThat(baseApi.getMessage()).isEqualTo(result.getMessage());

        verify(companyRepository).save(any());
    }

    @Test
    @DisplayName("기업 회원가입 실패 테스트 / 아이디 중복 - 서비스")
    void signUpCompanyFailByDuplicateEmail() {
        //given
        given(companyRepository.existsByEmail("austin@oracle.com")).willReturn(true);
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password(1234)
                .bno("111-13-14444")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();

        assertThatThrownBy(()-> companyService.signUpCompany(companySignUpRequestDto))
                .isInstanceOf(DuplicateKeyException.class);
    }
}