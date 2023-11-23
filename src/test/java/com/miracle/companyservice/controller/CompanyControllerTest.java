package com.miracle.companyservice.controller;

import com.google.gson.Gson;
import com.miracle.companyservice.dto.request.*;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.entity.PostType;
import com.miracle.companyservice.entity.Question;
import com.miracle.companyservice.service.CompanyServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
@MockBean(JpaMetamodelMappingContext.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CompanyServiceImpl companyService;


    @Test
    @DisplayName("이메일 중복 확인 성공 테스트")
    void checkEmail() throws Exception {
        String email = "austin@oracle.com";
        Gson gson = new Gson();
        String content = gson.toJson(new CompanyCheckEmailRequestDto(email));

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("사용가능한 이메일입니다.")
                .data(Boolean.TRUE)
                .build();

        given(companyService.checkEmailDuplicated(email)).willReturn(givenResponse);

        mockMvc.perform(
                        post("/v1/company/email")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(givenResponse.getHttpStatus()))
                .andExpect(jsonPath("$.message").value(givenResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(givenResponse.getData()))
                .andDo(print())
                .andReturn();

        verify(companyService).checkEmailDuplicated(email);
    }

    @Test
    @DisplayName("사업자 번호 중복 확인 성공 테스트")
    void checkBno() throws Exception {
        String bno = "111-22-33333";
        Gson gson = new Gson();
        String content = gson.toJson(new CompanyCheckBnoRequestDto(bno));

        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("가입 가능한 사업자 번호입니다.")
                .data(Boolean.TRUE)
                .build();

        given(companyService.checkBnoStatus(bno)).willReturn(givenResponse);

        mockMvc.perform(
                        post("/v1/company/bno")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(givenResponse.getHttpStatus()))
                .andExpect(jsonPath("$.message").value(givenResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(givenResponse.getData()))
                .andDo(print())
                .andReturn();

        verify(companyService).checkBnoStatus(bno);
    }


    @Test
    @DisplayName("기업 회원가입 성공 테스트")
    void signUpCompany() throws Exception {

        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password("password!")
                .bno("111-13-14444")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();

        given(companyService.signUpCompany(companySignUpRequestDto))
                .willReturn(SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원가입 성공")
                        .build());

        Gson gson = new Gson();
        String content = gson.toJson(companySignUpRequestDto);

        mockMvc.perform(
                        post("/v1/company/signup")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("200"))
                .andExpect(jsonPath("$.message").value("회원가입 성공"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print())
                .andReturn();
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginCompany() throws Exception {
        CompanyLoginRequestDto companyLoginRequestDto = new CompanyLoginRequestDto("austin@oracle.com", "123456!");
        SuccessApiResponse<Object> givenResponse = SuccessApiResponse.builder()
                .httpStatus(HttpStatus.OK.value())
                .message("로그인 성공")
                .data(33L)
                .build();

        Gson gson = new Gson();
        String content = gson.toJson(companyLoginRequestDto);

        given(companyService.loginCompany(any()))
                .willReturn(SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("로그인 성공")
                        .data(33L)
                        .build());

        mockMvc.perform(
                        post("/v1/company/login")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value(givenResponse.getHttpStatus()))
                .andExpect(jsonPath("$.message").value(givenResponse.getMessage()))
                .andExpect(jsonPath("$.data").value(givenResponse.getData()))
                .andDo(print())
                .andReturn();

        verify(companyService).loginCompany(companyLoginRequestDto);
    }


    @Test
    @DisplayName("토큰 불일치 예외 테스트")
    void unauthorizedTokenFail() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);

        CompanySignUpRequestDto companySignUpRequestDto = CompanySignUpRequestDto.builder()
                .name("오라클코리아")
                .email("austin@oracle.com")
                .password("password!")
                .bno("111-13-14444")
                .ceoName("오스틴강")
                .address("서울 서초구 효령로 335")
                .employeeNum(3000)
                .sector("소프트웨어 개발업")
                .photo("/사진/저장/경로.jpg")
                .introduction("데이터 베이스 소프트웨어 개발 및 공급을 하고 있는 글로벌 기업, 오라클입니다.")
                .build();

        given(companyService.signUpCompany(companySignUpRequestDto))
                .willReturn(ErrorApiResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .code("401")
                        .message("토큰 값이 일치하지 않습니다.")
                        .exception("UnauthorizedTokenException")
                        .build());

        String privateKey = "TkwkdsladkdlrhdnjfrmqdhodlfjgrpaksgdlwnjTdjdy";
        String sessionId = "sessionId";
        String result = sessionId + privateKey;
        int token = 000000000000;

        given(request.getHeader("sessionId")).willReturn(sessionId);
        given(request.getIntHeader("miracle")).willReturn(token);

        Gson gson = new Gson();
        String content = gson.toJson(companySignUpRequestDto);

        mockMvc.perform(
                        post("/v1/company/signup")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.httpStatus").value("401"))
                .andExpect(jsonPath("$.code").value("401"))
                .andExpect(jsonPath("$.message").value("토큰 값이 일치하지 않습니다."))
                .andExpect(jsonPath("$.exception").value("UnauthorizedTokenException"))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print())
                .andReturn();
    }


    @Test
    @DisplayName("공고 등록 테스트")
    void savePost() throws Exception {
        List<Question> questionList = new ArrayList<>();
        questionList.add(new Question("본인이 참여한 프로젝트 중 협업 경험을 기술하시오."));
        questionList.add(new Question("프로젝트를 진행하면서 본인이 직면한 어려운 기술적인 문제나 버그를 어떻게 해결했는지 기술하시오."));

        Set<Long> jobIdSet = new HashSet<>();
        jobIdSet.add(1L);
        jobIdSet.add(2L);
        jobIdSet.add(3L);

        Set<Long> stackIdSet = new HashSet<>();
        stackIdSet.add(1L);
        stackIdSet.add(2L);
        stackIdSet.add(3L);

        LocalDateTime endDate = LocalDateTime.of(2023,12,21, 18,00,00);

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .companyId(4L)
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
                .questionList(questionList)
                .jobIdSet(jobIdSet)
                .stackIdSet(stackIdSet)
                .build();

        given(companyService.savePost(postRequestDto))
                .willReturn(SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("공고가 성공적으로 등록되었습니다.")
                        .build());

        Gson gson = new Gson();
        String content = gson.toJson(postRequestDto);

        mockMvc.perform(
                        post("/v1/company/post/register")
                                .content(content)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("200"))
                .andExpect(jsonPath("$.message").value("공고가 성공적으로 등록되었습니다."))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print())
                .andReturn();
    }
}