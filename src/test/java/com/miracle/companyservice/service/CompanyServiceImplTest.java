package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.dto.response.CompanyLoginResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.repository.BnoRepository;
import com.miracle.companyservice.repository.CompanyFaqRepository;
import com.miracle.companyservice.repository.CompanyRepository;
import com.miracle.companyservice.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@SpringBootTest
class CompanyServiceImplTest {

    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private final CompanyFaqRepository companyFaqRepository = Mockito.mock(CompanyFaqRepository.class);
    private final BnoRepository bnoRepository = Mockito.mock(BnoRepository.class);
    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUpTest() {
        companyService = new CompanyServiceImpl(companyRepository, companyFaqRepository, bnoRepository, postRepository);
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

        SuccessApiResponse givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("회원가입 성공")
                .data(Boolean.TRUE)
                .build();
        given(companyRepository.existsByEmail(companySignUpRequestDto.getEmail())).willReturn(false);
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.signUpCompany(companySignUpRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).save(any());

    }

    @Test
    @DisplayName("기업 회원가입 실패 테스트")
    void signUpCompanyFail() {
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

        SuccessApiResponse givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("중복된 이메일입니다.")
                .data(Boolean.FALSE)
                .build();

        given(companyRepository.existsByEmail(companySignUpRequestDto.getEmail())).willReturn(true);
        Mockito.when(companyRepository.save(company)).thenReturn(company);
        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.signUpCompany(companySignUpRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companySignUpRequestDto.getEmail());
    }

    @Test
    @DisplayName("이메일 중복 확인 성공 테스트")
    void checkEmailDuplicated() {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(false);

        Boolean result = companyRepository.existsByEmail(email);

        Assertions.assertThat(result).isFalse();
        verify(companyRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 테스트")
    void checkEmailDuplicatedFail() {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(true);

        Boolean result = companyRepository.existsByEmail(email);

        Assertions.assertThat(result).isTrue();
        verify(companyRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("사업자 번호 확인 성공 테스트")
    void checkBnoStatus() {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("가입 가능한 사업자 번호입니다.")
                .data(Boolean.TRUE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(true);//사업자 등록이 되었는지 체크
        Mockito.when(bnoRepository.findStatusByBnoIsTrue(bno)).thenReturn(true); // 사업자 유효 여부 체크
        Mockito.when(companyRepository.existsByBno(bno)).thenReturn(false);//요청 사업자번호가 우리 서비스에 가입되었는지 체크

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.checkBnoStatus(bno);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

        verify(bnoRepository).existsByBno(bno);
        verify(bnoRepository).findStatusByBnoIsTrue(bno);
        verify(companyRepository).existsByBno(bno);
    }

    @Test
    @DisplayName("사업자 번호 확인 실패 테스트 1 / 미등록 사업자번호")
    void checkBnoStatusFail1() {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(false);//사업자 등록이 되었는지 체크

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.checkBnoStatus(bno);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

        verify(bnoRepository).existsByBno(bno);
    }

    @Test
    @DisplayName("사업자 번호 확인 실패 테스트 2 / 만료된 사업자번호")
    void checkBnoStatusFail2() {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("만료된 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(true);//사업자 등록이 되었는지 체크
        Mockito.when(bnoRepository.findStatusByBnoIsTrue(bno)).thenReturn(false); // 사업자 유효 여부 체크
        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.checkBnoStatus(bno);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

        verify(bnoRepository).existsByBno(bno);
        verify(bnoRepository).findStatusByBnoIsTrue(bno);
    }

    @Test
    @DisplayName("사업자 번호 확인 실패 테스트 3 / 이미 가입된 사업자 번호")
    void checkBnoStatusFail3() {
        String bno = "111-11-11111";

        SuccessApiResponse<Object> givenApiResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("이미 가입된 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(bnoRepository.existsByBno(bno)).thenReturn(true);//사업자 등록이 되었는지 체크
        Mockito.when(bnoRepository.findStatusByBnoIsTrue(bno)).thenReturn(true); // 사업자 유효 여부 체크
        Mockito.when(companyRepository.existsByBno(bno)).thenReturn(true);//요청 사업자번호가 우리 서비스에 가입되었는지 체크

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.checkBnoStatus(bno);
        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

        verify(bnoRepository).existsByBno(bno);
        verify(bnoRepository).findStatusByBnoIsTrue(bno);
        verify(companyRepository).existsByBno(bno);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginCompany() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");

        Company givenCompany = Company.builder()
                .id(33L)
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
                .faqList(new ArrayList<>())
                .build();

        CompanyLoginResponseDto companyLoginResponseDto = new CompanyLoginResponseDto(givenCompany.getId(), givenCompany.getEmail(), givenCompany.getBno());

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(companyLoginResponseDto)
                .build();


        given(companyRepository.existsByEmail(companyLoginRequestDto.getEmail())).willReturn(true); //아이디 일치 확인
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode())) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(true); // 사업자 번호 존재확인
        given(bnoRepository.findStatusByBnoIsTrue(givenCompany.getBno())).willReturn(true); // 사업자 만료 확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode()))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse successApiResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        Assertions.assertThat(successApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(successApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(successApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        verify(bnoRepository).existsByBno(givenCompany.getBno());
        verify(bnoRepository).findStatusByBnoIsTrue(givenCompany.getBno());
    }

    @Test
    @DisplayName("로그인 실패 테스트 1 / 아이디 불일치")
    void loginCompanyFail1() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");
        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                .data(Boolean.FALSE)
                .build();

        Company givenCompany = Company.builder()
                .id(33L)
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password("123456!")
                .bno("111-13-14444")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(new ArrayList<>())
                .build();


        given(companyRepository.existsByEmail(companyLoginRequestDto.getEmail())).willReturn(false); //아이디 일치 확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode()))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
    }

    @Test
    @DisplayName("로그인 실패 테스트 2 / 비밀번호 불일치")
    void loginCompanyFail2() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");
        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("이메일 또는 비밀번호가 일치하지 않습니다.")
                .data(Boolean.FALSE)
                .build();

        Optional<Company> givenCompany = Optional.empty();


        given(companyRepository.existsByEmail(companyLoginRequestDto.getEmail())).willReturn(true); //아이디 일치 확인
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode())) // 비밀번호 일치 확인
                .willReturn(givenCompany);

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode()))
                .thenReturn(givenCompany);

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
    }

    @Test
    @DisplayName("로그인 실패 테스트 3 / 미존재 사업자 번호")
    void loginCompanyFail3() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");
        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Company givenCompany = Company.builder()
                .id(33L)
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
                .faqList(new ArrayList<>())
                .build();


        given(companyRepository.existsByEmail(companyLoginRequestDto.getEmail())).willReturn(true); //아이디 일치 확인
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode())) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(false); // 사업자 번호 존재확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode()))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        verify(bnoRepository).existsByBno(givenCompany.getBno());
    }

    @Test
    @DisplayName("로그인 실패 테스트 4 / 사업자 번호 만료")
    void loginCompanyFail4() {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");
        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("만료된 사업자 번호입니다.")
                .data(Boolean.FALSE)
                .build();

        Company givenCompany = Company.builder()
                .id(33L)
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
                .faqList(new ArrayList<>())
                .build();


        given(companyRepository.existsByEmail(companyLoginRequestDto.getEmail())).willReturn(true); //아이디 일치 확인
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode())) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(true); // 사업자 번호 존재확인
        given(bnoRepository.findStatusByBnoIsTrue(givenCompany.getBno())).willReturn(false); // 사업자 만료 확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode()))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        Assertions.assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), companyLoginRequestDto.getPassword().hashCode());
        verify(bnoRepository).existsByBno(givenCompany.getBno());
        verify(bnoRepository).findStatusByBnoIsTrue(givenCompany.getBno());
    }

    @Test
    @DisplayName("최신 / 마감임박 공고 3건 조회 성공")
    void postForMainPage() {

    }
}