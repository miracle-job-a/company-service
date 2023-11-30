package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyFaqRequestDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.*;
import com.miracle.companyservice.entity.*;
import com.miracle.companyservice.repository.*;
import com.miracle.companyservice.util.encryptor.PasswordEncryptor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.*;


@SpringBootTest
class CompanyServiceImplTest {

    private final CompanyRepository companyRepository = Mockito.mock(CompanyRepository.class);
    private final CompanyFaqRepository companyFaqRepository = Mockito.mock(CompanyFaqRepository.class);
    private final BnoRepository bnoRepository = Mockito.mock(BnoRepository.class);
    private final PostRepository postRepository = Mockito.mock(PostRepository.class);
    private final QuestionRepository questionRepository = Mockito.mock(QuestionRepository.class);
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setUpTest() {
        companyService = new CompanyServiceImpl(companyRepository, companyFaqRepository, bnoRepository, postRepository, questionRepository);
    }

    @Test
    @DisplayName("회원 검증 - 인터셉터")
    void companyValidation() {
        long id = 1L;
        String email = "austin@oracle.com";
        String bno = "111-11-11111";

        Mockito.when(companyRepository.existsByIdAndEmailAndBno(id, email, bno)).thenReturn(true);

        Boolean result = companyService.companyValidation(id, email, bno);

        assertThat(result).isTrue();
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

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

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

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companySignUpRequestDto.getEmail());
    }

    @Test
    @DisplayName("이메일 중복 확인 성공 테스트")
    void checkEmailDuplicated() {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(false);

        Boolean result = companyRepository.existsByEmail(email);

        assertThat(result).isFalse();
        verify(companyRepository).existsByEmail(email);
    }

    @Test
    @DisplayName("이메일 중복 확인 실패 테스트")
    void checkEmailDuplicatedFail() {
        String email = "austin@oracle.com";

        Mockito.when(companyRepository.existsByEmail(email)).thenReturn(true);

        Boolean result = companyRepository.existsByEmail(email);

        assertThat(result).isTrue();
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

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

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

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

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

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

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
        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenApiResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenApiResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenApiResponse.getData());

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
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()))) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(true); // 사업자 번호 존재확인
        given(bnoRepository.findStatusByBnoIsTrue(givenCompany.getBno())).willReturn(true); // 사업자 만료 확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword())))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse successApiResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        assertThat(successApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(successApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(successApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()));
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

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword())))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

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
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()))) // 비밀번호 일치 확인
                .willReturn(givenCompany);

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword())))
                .thenReturn(givenCompany);

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()));
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
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()))) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(false); // 사업자 번호 존재확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword())))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()));
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
        given(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()))) // 비밀번호 일치 확인
                .willReturn(Optional.of(givenCompany));
        given(bnoRepository.existsByBno(givenCompany.getBno())).willReturn(true); // 사업자 번호 존재확인
        given(bnoRepository.findStatusByBnoIsTrue(givenCompany.getBno())).willReturn(false); // 사업자 만료 확인

        Mockito.when(companyRepository.findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword())))
                .thenReturn(Optional.of(givenCompany));

        SuccessApiResponse resultResponse = (SuccessApiResponse) companyService.loginCompany(companyLoginRequestDto);

        assertThat(resultResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(resultResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(resultResponse.getData()).isEqualTo(givenResponse.getData());

        verify(companyRepository).existsByEmail(companyLoginRequestDto.getEmail());
        verify(companyRepository).findByEmailAndPassword(companyLoginRequestDto.getEmail(), PasswordEncryptor.SHA3Algorithm(companyLoginRequestDto.getPassword()));
        verify(bnoRepository).existsByBno(givenCompany.getBno());
        verify(bnoRepository).findStatusByBnoIsTrue(givenCompany.getBno());
    }

    @Test
    @DisplayName("최신 / 마감임박 공고 3건 조회 성공")
    void postForMainPage() {
        Set<Long> jobIdSet = new HashSet<>();
        Set<Long> stackIdSet = new HashSet<>();

        Post post1 = Post.builder()
                .id(1L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 12, 21, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post1.setCreatedAt(LocalDateTime.of(2023, 11, 21, 18, 00, 00));

        Post post2 = Post.builder()
                .id(2L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 12, 22, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post2.setCreatedAt(LocalDateTime.of(2023, 11, 22, 18, 00, 00));

        Post post3 = Post.builder()
                .id(3L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 12, 23, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post3.setCreatedAt(LocalDateTime.of(2023, 12, 23, 18, 00, 00));

        Post post4 = Post.builder()
                .id(4L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 11, 20, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post4.setCreatedAt(LocalDateTime.of(2023, 11, 10, 18, 00, 00));

        Post post5 = Post.builder()
                .id(5L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 11, 21, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post5.setCreatedAt(LocalDateTime.of(2023, 11, 10, 18, 00, 00));

        Post post6 = Post.builder()
                .id(6L)
                .companyId(1L)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(LocalDateTime.of(2023, 11, 22, 18, 00, 00))
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post6.setCreatedAt(LocalDateTime.of(2023, 11, 10, 18, 00, 00));
        List<Post> newestResult = new ArrayList<>(List.of(post1, post2, post3));
        List<Post> deadlineResult = new ArrayList<>(List.of(post4, post5, post6));

        List<MainPagePostsResponseDto> newest = new ArrayList<>();
        newestResult.iterator().forEachRemaining((Post p) -> {
            String photo = companyRepository.findPhotoById(p.getCompanyId());
            newest.add(new MainPagePostsResponseDto(p, photo));
        });

        List<MainPagePostsResponseDto> deadline = new ArrayList<>();
        deadlineResult.iterator().forEachRemaining((Post p) -> {
            String photo = companyRepository.findPhotoById(p.getCompanyId());
            deadline.add(new MainPagePostsResponseDto(p, photo));
        });

        Map<String, Object> data = new HashMap<>();
        data.put("newest", newest);
        data.put("deadline", deadline);

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("최신 공고, 마감임박 공고 조회 성공")
                .data(data)
                .build();

        given(postRepository.findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 3))).willReturn(newestResult);
        given(postRepository.findTop3ByEndDateOrderByEndDateAsc(PageRequest.of(0, 3))).willReturn(deadlineResult);
        given(companyRepository.findPhotoById(any())).willReturn(null);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.postForMainPage();

        Assertions.assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(postRepository).findAllByClosedFalseAndDeletedFalseOrderByCreatedAtDesc(PageRequest.of(0, 3));
        verify(postRepository).findTop3ByEndDateOrderByEndDateAsc(PageRequest.of(0, 3));
        verify(companyRepository, times(12)).findPhotoById(any());
    }

    @Test
    @DisplayName("FAQ 추가 성공")
    void addFaq() {
        long companyId = 99L;

        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(new ArrayList<>())
                .build();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 등록 성공")
                .data(1L)
                .build();

        CompanyFaqRequestDto companyFaqRequestDto = new CompanyFaqRequestDto("질문1", "답변1");

        given(companyRepository.findById(companyId)).willReturn(Optional.of(givenCompany)); // company 존재여부 확인
        given(companyFaqRepository.countByCompanyId(companyId)).willReturn(0L); // 현재 등록된 faq 개수 확인

        CompanyFaq companyFaq = new CompanyFaq(companyFaqRequestDto.getQuestion(), companyFaqRequestDto.getAnswer(), givenCompany);

        CompanyFaq savedCompanyFaq = new CompanyFaq(companyFaqRequestDto.getQuestion(), companyFaqRequestDto.getAnswer(), givenCompany);
        savedCompanyFaq.setId(1L);

        Mockito.when(companyFaqRepository.save(companyFaq)).thenReturn(savedCompanyFaq);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.addFaq(companyId, companyFaqRequestDto);
        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyRepository).findById(companyId);
        verify(companyFaqRepository).countByCompanyId(companyId);
        verify(companyFaqRepository).save(companyFaq);
    }

    @Test
    @DisplayName("FAQ 추가 실패1 / FAQ 10개")
    void addFaqFail1() {
        long companyId = 99L;

        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(new ArrayList<>())
                .build();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("FAQ는 10개를 넘을 수 없습니다.")
                .data(Boolean.FALSE)
                .build();

        CompanyFaqRequestDto companyFaqRequestDto = new CompanyFaqRequestDto("질문1", "답변1");

        given(companyRepository.findById(companyId)).willReturn(Optional.of(givenCompany)); // company 존재여부 확인
        given(companyFaqRepository.countByCompanyId(companyId)).willReturn(10L); // 현재 등록된 faq 개수 확인

        Mockito.when(companyFaqRepository.countByCompanyId(companyId)).thenReturn(10L);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.addFaq(companyId, companyFaqRequestDto);
        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyRepository).findById(companyId);
        verify(companyFaqRepository).countByCompanyId(companyId);
    }

    @Test
    @DisplayName("FAQ 추가 실패2 / 미존재 companyId")
    void addFaqFail2() {
        long companyId = 33L;

        Optional<Company> givenCompany = Optional.empty();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 companyId 입니다.")
                .data(Boolean.FALSE)
                .build();

        CompanyFaqRequestDto companyFaqRequestDto = new CompanyFaqRequestDto("질문1", "답변1");

        given(companyRepository.findById(companyId)).willReturn(givenCompany); // company 존재여부 확인

        Mockito.when(companyRepository.findById(companyId)).thenReturn(givenCompany);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.addFaq(companyId, companyFaqRequestDto);
        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyRepository).findById(companyId);

    }

    @Test
    @DisplayName("FAQ 삭제 성공")
    void deleteFaq() {
        long givenCompanyId = 99L;
        long givenFaqId = 9L;
        List<CompanyFaq> faqList = new ArrayList<>();
        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(faqList)
                .build();

        CompanyFaq givenCompanyFaq = new CompanyFaq("질문1", "답변", givenCompany);
        givenCompanyFaq.setId(givenFaqId);
        faqList.add(givenCompanyFaq);

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 삭제 성공")
                .data(Boolean.TRUE)
                .build();

        given(companyFaqRepository.existsById(givenFaqId)).willReturn(true); //faq 존재여부 확인
        given(companyFaqRepository.existsByCompanyIdAndId(givenCompanyId, givenFaqId)).willReturn(true); //faq의 companyId와 매개변수 companyId 일치 여부 확인

        //when
        Mockito.doNothing().when(companyFaqRepository).deleteById(givenFaqId);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.deleteFaq(givenCompanyId, givenFaqId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyFaqRepository).existsById(givenFaqId);
        verify(companyFaqRepository).existsByCompanyIdAndId(givenCompanyId, givenFaqId);
        verify(companyFaqRepository).deleteById(givenFaqId);

    }

    @Test
    @DisplayName("FAQ 삭제 실패1 / 기업 불일치")
    void deleteFaqFail1() {
        long givenCompanyId = 99L;
        long givenFaqId = 9L;
        List<CompanyFaq> faqList = new ArrayList<>();
        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(faqList)
                .build();

        CompanyFaq givenCompanyFaq = new CompanyFaq("질문1", "답변", givenCompany);
        givenCompanyFaq.setId(givenFaqId);
        faqList.add(givenCompanyFaq);

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("companyId와 삭제하려는 faq의 compnayId가 일치하지 않습니다.")
                .data(Boolean.FALSE)
                .build();

        given(companyFaqRepository.existsById(givenFaqId)).willReturn(true); //faq 존재여부 확인
        given(companyFaqRepository.existsByCompanyIdAndId(givenCompanyId, givenFaqId)).willReturn(false); //faq의 companyId와 매개변수 companyId 일치 여부 확인

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.deleteFaq(givenCompanyId, givenFaqId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyFaqRepository).existsById(givenFaqId);
        verify(companyFaqRepository).existsByCompanyIdAndId(givenCompanyId, givenFaqId);
    }

    @Test
    @DisplayName("FAQ 삭제 실패2 / 미존재 FAQ")
    void deleteFaqFail2() {
        long givenCompanyId = 99L;
        long givenFaqId = 9L;
        List<CompanyFaq> faqList = new ArrayList<>();
        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(faqList)
                .build();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 faqId 입니다.")
                .data(Boolean.FALSE)
                .build();

        given(companyFaqRepository.existsById(givenFaqId)).willReturn(false); //faq 존재여부 확인

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.deleteFaq(givenCompanyId, givenFaqId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyFaqRepository).existsById(givenFaqId);
    }

    @Test
    @DisplayName("FAQ 조회 성공")
    void getFaq() {
        long givenCompanyId = 99L;
        long givenFaqId = 9L;
        List<CompanyFaq> faqList = new ArrayList<>();
        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(faqList)
                .build();

        CompanyFaq givenCompanyFaq = new CompanyFaq("질문1", "답변", givenCompany);
        givenCompanyFaq.setId(givenFaqId);
        faqList.add(givenCompanyFaq);

        List<CompanyFaqResponseDto> faqDtoList = new ArrayList<>();

        faqList.iterator().forEachRemaining((CompanyFaq c) ->
                faqDtoList.add(new CompanyFaqResponseDto(c.getId(), c.getQuestion(), c.getAnswer())));

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("FAQ 조회 성공")
                .data(faqDtoList)
                .build();

        given(companyRepository.existsById(givenCompanyId)).willReturn(true);

        Mockito.when(companyFaqRepository.findByCompanyId(givenCompanyId)).thenReturn(faqList);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getFaq(givenCompanyId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyRepository).existsById(givenCompanyId);
        verify(companyFaqRepository).findByCompanyId(givenCompanyId);
    }

    @Test
    @DisplayName("FAQ 조회 실패 / 미존재 기업")
    void getFaqFail() {
        long givenCompanyId = 99L;
        long givenFaqId = 9L;
        List<CompanyFaq> faqList = new ArrayList<>();
        Company givenCompany = Company.builder()
                .id(99L)
                .name("오라클코리아")
                .email("austin999@oracle.com")
                .password("password!")
                .bno("111-22-77777")
                .ceoName("오스틴 강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .faqList(faqList)
                .build();

        CompanyFaq givenCompanyFaq = new CompanyFaq("질문1", "답변", givenCompany);
        givenCompanyFaq.setId(givenFaqId);
        faqList.add(givenCompanyFaq);

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 companyId 입니다.")
                .data(Boolean.FALSE)
                .build();

        given(companyRepository.existsById(givenCompanyId)).willReturn(false);

        Mockito.when(companyFaqRepository.findByCompanyId(givenCompanyId)).thenReturn(faqList);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getFaq(givenCompanyId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(companyRepository).existsById(givenCompanyId);

    }

    @Test
    @DisplayName("자소서 질문 조회 성공")
    void returnQuestion() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        List<Question> givenQuestionList = new ArrayList<>();
        Set<Long> jobIdSet = new HashSet<>();
        Set<Long> stackIdSet = new HashSet<>();

        LocalDateTime endDate = LocalDateTime.of(2023, 12, 21, 18, 00, 00);

        Post post = Post.builder()
                .id(givenPostId)
                .companyId(givenCompanyId)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(endDate)
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();
        post.setQuestionList(givenQuestionList);

        givenQuestionList.add(new Question("본인이 참여한 프로젝트 중 협업 경험을 기술하시오.", post));
        givenQuestionList.add(new Question("프로젝트를 진행하면서 본인이 직면한 어려운 기술적인 문제나 버그를 어떻게 해결했는지 기술하시오.", post));

        List<QuestionResponseDto> questionResponseDtoList = new ArrayList<>();
        givenQuestionList.iterator().forEachRemaining((Question q) ->
                questionResponseDtoList.add(new QuestionResponseDto(q)));

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("자기소개서 질문 조회 성공")
                .data(questionResponseDtoList)
                .build();

        given(postRepository.existsByCompanyIdAndId(givenCompanyId, givenPostId)).willReturn(true);
        Mockito.when(postRepository.findById(givenPostId)).thenReturn(Optional.of(post));

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.returnQuestions(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
        verify(postRepository).findById(givenPostId);
    }

    @Test
    @DisplayName("자소서 질문 조회 실패1 / 미존재 공고")
    void returnQuestionFail1() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        Optional<Post> post = Optional.empty();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("존재하지 않는 공고입니다.")
                .data(Boolean.FALSE)
                .build();

        given(postRepository.existsByCompanyIdAndId(givenCompanyId, givenPostId)).willReturn(true);
        Mockito.when(postRepository.findById(givenPostId)).thenReturn(post);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.returnQuestions(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
    }

    @Test
    @DisplayName("자소서 질문 조회 실패2 / 기업 불일치")
    void returnQuestionFail2() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        Optional<Post> post = Optional.empty();

        SuccessApiResponse<Object> givenResult = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("companyId가 공고의 companyId 값과 다릅니다.")
                .data(Boolean.FALSE)
                .build();

        given(postRepository.existsByCompanyIdAndId(givenCompanyId, givenPostId)).willReturn(false);
        Mockito.when(postRepository.findById(givenPostId)).thenReturn(post);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.returnQuestions(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResult.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResult.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResult.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
    }

    @Test
    @DisplayName("기업관리 공고수 조회 성공")
    void getCountPosts() {
        long givenCompanyId = 99L;

        long countAllPosts = 3L;
        long countEndedPosts = 2L;
        long countOpen = 1L;

        Map<String, Long> map = new HashMap<>();
        map.put("countAllPosts", countAllPosts);
        map.put("countEndedPosts", countEndedPosts);
        map.put("countOpen", countOpen);

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고 수 조회 완료")
                .data(map)
                .build();

        given(postRepository.countByCompanyIdAndDeletedFalse(givenCompanyId)).willReturn(countAllPosts);
        given(postRepository.countByCompanyIdAndClosedTrueAndDeletedFalse(givenCompanyId)).willReturn(countEndedPosts);
        given(postRepository.countByCompanyIdAndClosedFalseAndDeletedFalse(givenCompanyId)).willReturn(countOpen);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getCountPosts(givenCompanyId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(postRepository).countByCompanyIdAndDeletedFalse(givenCompanyId);
        verify(postRepository).countByCompanyIdAndClosedTrueAndDeletedFalse(givenCompanyId);
        verify(postRepository).countByCompanyIdAndClosedFalseAndDeletedFalse(givenCompanyId);
    }

    @Test
    @DisplayName("공고 마감 성공")
    void changeToClose() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        Set<Long> jobIdSet = new HashSet<>();
        Set<Long> stackIdSet = new HashSet<>();

        LocalDateTime endDate = LocalDateTime.of(2023, 12, 21, 18, 00, 00);

        Post post = Post.builder()
                .id(givenPostId)
                .companyId(givenCompanyId)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(endDate)
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(false)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();

        Post givenPost = Post.builder()
                .id(givenPostId)
                .companyId(givenCompanyId)
                .postType(PostType.NORMAL)
                .title("오라클코리아")
                .endDate(endDate)
                .mainTask("서버 사이드 로직 개발 및 유지보수")
                .workCondition("주 5일, 오전 9시 ~ 오후 6시")
                .qualification("자바 경험자, 스프링 프레임워크 경험자 우대")
                .tool("eclipse")
                .benefit("4대 보험, 퇴직금")
                .process("서류 접수 -> 1차 면접 -> 코딩 테스트 -> 최종 합격")
                .notice("지원 후에는 지원취소가 불가합니다.")
                .specialSkill("Java, Spring")
                .workAddress("서울특별시 용산구")
                .closed(true)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("공고가 마감처리 되었습니다.")
                .data(Boolean.TRUE)
                .build();

        given(postRepository.existsByCompanyIdAndId(givenCompanyId, givenPostId)).willReturn(true);
        given(postRepository.findById(givenPostId)).willReturn(Optional.of(post));

        Mockito.when(postRepository.save(post)).thenReturn(givenPost);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.changeToClose(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
        verify(postRepository).findById(givenPostId);
        verify(postRepository).save(post);
    }

    @Test
    @DisplayName("공고 마감 실패1 / 미존재 공고")
    void changeToCloseFail1() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        Optional<Post> post = Optional.empty();

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("공고가 존재하지 않습니다.")
                .data(Boolean.FALSE)
                .build();

        given(postRepository.existsByCompanyIdAndId(givenCompanyId, givenPostId)).willReturn(true);

        Mockito.when(postRepository.findById(givenPostId)).thenReturn(post);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.changeToClose(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
        verify(postRepository).findById(givenPostId);
    }

    @Test
    @DisplayName("공고 마감 실패2 / 기업 불일치")
    void changeToCloseFail2() {
        long givenCompanyId = 99L;
        long givenPostId = 3L;

        Optional<Post> post = Optional.empty();

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .message("companyId가 공고의 companyId 값과 다릅니다.")
                .data(Boolean.FALSE)
                .build();

        Mockito.when(postRepository.existsByCompanyIdAndId(givenPostId, givenPostId)).thenReturn(false);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.changeToClose(givenCompanyId, givenPostId);

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        verify(postRepository).existsByCompanyIdAndId(givenCompanyId, givenPostId);
    }

    @Test
    @DisplayName("최신 공고 정렬")
    void getLatestPosts() {
        long givenCompanyId = 97L;
        Set<Long> jobIdSet = new HashSet<>();
        jobIdSet.add(1L);
        List<ManagePostsResponseDto> baseData = new ArrayList<>();
        baseData.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "마감4", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        baseData.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "마감5", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate
        // 3,2,1 - 4,5 순으로 나와야 함.
        List<ManagePostsResponseDto> sortedLatest = new ArrayList<>();
        sortedLatest.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        sortedLatest.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        sortedLatest.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        sortedLatest.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "마감4", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        sortedLatest.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "마감5", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("최신 공고 정렬")
                .data(sortedLatest)
                .build();

        Mockito.when(postRepository.findAllByCompanyIdOrderByLatest(givenCompanyId)).thenReturn(sortedLatest);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getLatestPosts(givenCompanyId);

        List<ManagePostsResponseDto> actualData = (List<ManagePostsResponseDto>) commonApiResponse.getData();

        assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        Assertions.assertThat(actualData)
                .isSortedAccordingTo(Comparator.comparing(ManagePostsResponseDto::getClosed)
                        .thenComparing(ManagePostsResponseDto::getCreatedAt, Comparator.reverseOrder()));

        verify(postRepository).findAllByCompanyIdOrderByLatest(givenCompanyId);
    }

    @Test
    @DisplayName("마감 임박 공고 정렬")
    void getDeadlinePosts() {
        long givenCompanyId = 99L;
        Set<Long> jobIdSet = new HashSet<>();
        jobIdSet.add(1L);

        List<ManagePostsResponseDto> baseData = new ArrayList<>();
        baseData.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "공고4", jobIdSet,
                LocalDateTime.of(2023, 11, 19, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 23, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "공고5", jobIdSet,
                LocalDateTime.of(2023, 11, 18, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 24, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(6L, PostType.NORMAL, "마감6", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        baseData.add(new ManagePostsResponseDto(7L, PostType.NORMAL, "마감7", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate
        //1 2 4 5 3 - 6 7순서로 나와야함.
        List<ManagePostsResponseDto> sortedDeadline = new ArrayList<>();
        sortedDeadline.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "공고4", jobIdSet,
                LocalDateTime.of(2023, 11, 19, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 23, 0, 0), false)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "공고5", jobIdSet,
                LocalDateTime.of(2023, 11, 18, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 24, 0, 0), false)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(6L, PostType.NORMAL, "마감6", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        sortedDeadline.add(new ManagePostsResponseDto(7L, PostType.NORMAL, "마감7", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate


        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("마감 임박 공고 정렬")
                .data(sortedDeadline)
                .build();

        Mockito.when(postRepository.findAllByCompanyIdOrderByDeadline(givenCompanyId)).thenReturn(sortedDeadline);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getDeadlinePosts(givenCompanyId);

        List<ManagePostsResponseDto> actualData = (List<ManagePostsResponseDto>) commonApiResponse.getData();

        Assertions.assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        Assertions.assertThat(actualData)
                .isSortedAccordingTo(Comparator.comparing(ManagePostsResponseDto::getClosed)
                        .thenComparing(ManagePostsResponseDto::getEndDate));

        verify(postRepository).findAllByCompanyIdOrderByDeadline(givenCompanyId);
    }

    @Test
    @DisplayName("마감 공고만 보기")
    void getEndPosts() {
        long givenCompanyId = 99L;
        Set<Long> jobIdSet = new HashSet<>();
        jobIdSet.add(1L);
        List<ManagePostsResponseDto> baseData = new ArrayList<>();
        baseData.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "마감4", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        baseData.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "마감5", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate
        // 4 - 5 순서로 보여야함
        List<ManagePostsResponseDto> sortedEnd = new ArrayList<>();
        sortedEnd.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "마감4", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        sortedEnd.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "마감5", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("마감 공고만 보기")
                .data(sortedEnd)
                .build();

        Mockito.when(postRepository.findAllByCompanyIdOrderByEnd(givenCompanyId)).thenReturn(sortedEnd);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getEndPosts(givenCompanyId);

        Assertions.assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        Assertions.assertThat(sortedEnd)
                .isSortedAccordingTo(Comparator.comparing(ManagePostsResponseDto::getCreatedAt, Comparator.reverseOrder()));


        verify(postRepository).findAllByCompanyIdOrderByEnd(givenCompanyId);
    }

    @Test
    @DisplayName("진행중 공고만 보기")
    void getOpenPosts() {
        long givenCompanyId = 99L;
        Set<Long> jobIdSet = new HashSet<>();
        jobIdSet.add(1L);
        List<ManagePostsResponseDto> baseData = new ArrayList<>();
        baseData.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        baseData.add(new ManagePostsResponseDto(4L, PostType.NORMAL, "마감4", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 11, 25, 0, 0), true)); //EndDate
        baseData.add(new ManagePostsResponseDto(5L, PostType.NORMAL, "마감5", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), true)); //EndDate
        // 3 2 1 순서로 보여야함
        List<ManagePostsResponseDto> sortedOpen = new ArrayList<>();
        sortedOpen.add(new ManagePostsResponseDto(3L, PostType.NORMAL, "공고3", jobIdSet,
                LocalDateTime.of(2023, 11, 25, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 25, 0, 0), false)); //EndDate
        sortedOpen.add(new ManagePostsResponseDto(2L, PostType.NORMAL, "공고2", jobIdSet,
                LocalDateTime.of(2023, 11, 23, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 10, 0, 0), false)); //EndDate
        sortedOpen.add(new ManagePostsResponseDto(1L, PostType.NORMAL, "공고1", jobIdSet,
                LocalDateTime.of(2023, 11, 20, 0, 0), //createdAt
                LocalDateTime.of(2023, 12, 5, 0, 0), false)); //EndDate

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("마감 공고만 보기")
                .data(sortedOpen)
                .build();

        Mockito.when(postRepository.findAllByCompanyIdOrderByEnd(givenCompanyId)).thenReturn(sortedOpen);

        SuccessApiResponse commonApiResponse = (SuccessApiResponse) companyService.getEndPosts(givenCompanyId);

        Assertions.assertThat(commonApiResponse.getHttpStatus()).isEqualTo(givenResponse.getHttpStatus());
        Assertions.assertThat(commonApiResponse.getMessage()).isEqualTo(givenResponse.getMessage());
        Assertions.assertThat(commonApiResponse.getData()).isEqualTo(givenResponse.getData());

        List<ManagePostsResponseDto> actualData = (List<ManagePostsResponseDto>) commonApiResponse.getData();

        Assertions.assertThat(actualData)
                .isSortedAccordingTo(Comparator.comparing(ManagePostsResponseDto::getCreatedAt, Comparator.reverseOrder()));

        verify(postRepository).findAllByCompanyIdOrderByEnd(givenCompanyId);
    }
}