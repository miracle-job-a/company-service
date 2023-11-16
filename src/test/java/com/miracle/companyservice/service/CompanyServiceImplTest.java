package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.repository.BnoRepository;
import com.miracle.companyservice.repository.CompanyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@SpringBootTest
class CompanyServiceImplTest {

    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private final BnoRepository bnoRepository = Mockito.mock(BnoRepository.class);
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUpTest() {
        companyService = new CompanyServiceImpl(companyRepository, bnoRepository);
    }

    @Test
    @DisplayName("기업 회원가입 성공 테스트")
    void signUpCompany() {
        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password("password!")
                .bno("111-13-14444")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();

        Company company = new Company(companySignUpRequestDto);

        ApiResponse result = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("회원가입 성공")
                .build();

        Mockito.when(companyRepository.save(company)).thenReturn(company);
        ApiResponse successApiResponse = companyService.signUpCompany(companySignUpRequestDto);

        Assertions.assertThat(successApiResponse.getHttpStatus()).isEqualTo(result.getHttpStatus());
        Assertions.assertThat(successApiResponse.getMessage()).isEqualTo(result.getMessage());

        verify(companyRepository).save(any());
    }

    @Test
    @DisplayName("이메일 중복 확인 성공 테스트")
    void checkEmailDuplicated () {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(false);

        Boolean result = companyRepository.existsByEmail(email);

        Assertions.assertThat(result).isFalse();
        verify(companyRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 테스트")
    void checkEmailDuplicatedFail () {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(true);

        Boolean result = companyRepository.existsByEmail(email);

        Assertions.assertThat(result).isTrue();
        verify(companyRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("사업자 번호 확인 성공 테스트")
    void checkBnoStatus () {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("가입 가능한 사업자 번호입니다.")
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(true);//사업자 등록이 되었는지 체크
        Mockito.when(companyRepository.existsByBno(bno)).thenReturn(false);//요청 사업자번호가 우리 서비스에 가입되었는지 체크

        ApiResponse result = companyService.checkBnoStatus(bno);
        Assertions.assertThat(result.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(result.getMessage()).isEqualTo(givenApiResponse.getMessage());

        verify(bnoRepository).existsByBno(bno);
        verify(companyRepository).existsByBno(bno);
    }

    @Test
    @DisplayName("사업자 번호 확인 실패 테스트 1 / 미등록 사업자번호")
    void checkBnoStatusFail1 () {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(false);//사업자 등록이 되었는지 체크

        ApiResponse result = companyService.checkBnoStatus(bno);

        Assertions.assertThat(result.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(result.getMessage()).isEqualTo(givenApiResponse.getMessage());

        verify(bnoRepository).existsByBno(bno);
    }

    @Test
    @DisplayName("사업자 번호 확인 실패 테스트 2 / 이미 가입된 사업자 번호")
    void checkBnoStatusFail2 () {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("이미 가입된 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(true);//사업자 등록이 되었는지 체크
        Mockito.when(companyRepository.existsByBno(bno)).thenReturn(true);//요청 사업자번호가 우리 서비스에 가입되었는지 체크

        ApiResponse result = companyService.checkBnoStatus(bno);
        Assertions.assertThat(result.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(result.getMessage()).isEqualTo(givenApiResponse.getMessage());

        verify(bnoRepository).existsByBno(bno);
        verify(companyRepository).existsByBno(bno);
    }





}