package com.miracle.companyservice.util.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.repository.CompanyRepository;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    @Autowired
    public AuthorizationInterceptor(CompanyServiceImpl companyServiceImpl, CompanyRepository companyRepository) {
        this.companyService = companyServiceImpl;
        this.companyRepository = companyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        String pathCompanyId = pathVariables.get("companyId");

        if (request.getHeader("Company-Id") == null) {
            sendFailResponseByHeader(response);
            return false;
        }
        if (!pathCompanyId.equals(request.getHeader("Company-Id"))) {
            sendFailResponseByForbidden(response);
            return false;
        }
        Long id = Long.parseLong(request.getHeader("Company-Id"));
        if (!companyRepository.existsById(id)) {
            sendFailResponseByNotExist(response);
            return false;
        }

        if (request.getHeader("Company-Email") == null) {
            sendFailResponseByHeader(response);
            return false;
        }
        String email = request.getHeader("Company-Email");
        if (request.getHeader("Company-Bno") == null) {
            sendFailResponseByHeader(response);
            return false;
        }
        String bno = request.getHeader("Company-Bno");
        if (!companyService.companyValidation(id, email, bno)) {
            sendFailResponse(response);
            return false;
        }
        return true;
    }

    private void sendFailResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(
                SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .message("회원 인증에 실패하여, 정보를 요청할 수 없습니다.")
                        .data(Boolean.FALSE)
                        .build()
        ));
        writer.flush();
    }

    private void sendFailResponseByHeader(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(
                SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.UNAUTHORIZED.value())
                        .message("회원 인증 정보에 빈 값이 있습니다.")
                        .data(Boolean.FALSE)
                        .build()
        ));
        writer.flush();
    }

    private void sendFailResponseByForbidden(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(
                SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.FORBIDDEN.value())
                        .message("접근 권한이 없습니다.")
                        .data(Boolean.FALSE)
                        .build()
        ));
        writer.flush();
    }

    private void sendFailResponseByNotExist(HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(
                SuccessApiResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST.value())
                        .message("존재하지 않는 companyId 입니다.")
                        .data(Boolean.FALSE)
                        .build()
        ));
        writer.flush();
    }
}
