package com.miracle.companyservice.controller;

import com.google.gson.Gson;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ErrorApiResponse;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.service.CompanyServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import javax.servlet.http.HttpServletRequest;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CompanyServiceImpl companyService;

    @Test
    @DisplayName("기업 회원가입 테스트 - 컨트롤러")
    void signUpCompany() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);

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

        given(companyService.signUpCompany(companySignUpRequestDto))
                .willReturn(SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원가입 성공")
                        .build());

        String privateKey = "TkwkdsladkdlrhdnjfrmqdhodlfjgrpaksgdlwnjTdjdy";
        String sessionId = "sessionId";
        String result = sessionId + privateKey;
        int token = result.hashCode();

        given(request.getHeader("sessionId")).willReturn(sessionId);
        given(request.getIntHeader("miracle")).willReturn(token);

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
    @DisplayName("기업 회원가입 테스트 실패 - 컨트롤러")
    void signUpCompanyFail() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);

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

        given(companyService.signUpCompany(companySignUpRequestDto))
                .willReturn(ErrorApiResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .code("401")
                        .message("토큰 값이 일치하지 않습니다.")
                        .exception("[UnauthorizedTokenException]: 토큰 값이 일치하지 않습니다.")
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.httpStatus").value("401"))
                .andExpect(jsonPath("$.code").value("401"))
                .andExpect(jsonPath("$.message").value("토큰 값이 일치하지 않습니다."))
                .andExpect(jsonPath("$.exception").value("[UnauthorizedTokenException]: 토큰 값이 일치하지 않습니다."))
                .andExpect(jsonPath("$.data").doesNotExist())
                .andDo(print())
                .andReturn();
    }

}