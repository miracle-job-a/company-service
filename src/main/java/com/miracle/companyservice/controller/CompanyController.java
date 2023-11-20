package com.miracle.companyservice.controller;


import com.miracle.companyservice.controller.swagger.ApiCheckBno;
import com.miracle.companyservice.controller.swagger.ApiCheckEmail;
import com.miracle.companyservice.controller.swagger.ApiLogin;
import com.miracle.companyservice.controller.swagger.ApiSignUp;
import com.miracle.companyservice.dto.request.CompanyCheckBnoRequestDto;
import com.miracle.companyservice.dto.request.CompanyCheckEmailRequestDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyServiceImpl;
    }


    @ApiCheckEmail
    @PostMapping("/email")
    public CommonApiResponse checkEmail(@Valid @RequestBody CompanyCheckEmailRequestDto companyCheckEmailRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.checkEmailDuplicated(companyCheckEmailRequestDto.getEmail());
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiCheckBno
    @PostMapping("/bno")
    public CommonApiResponse checkBno(@Valid @RequestBody CompanyCheckBnoRequestDto companyCheckBnoRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.checkBnoStatus(companyCheckBnoRequestDto.getBno());
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiSignUp
    @PostMapping("/signup")
    public CommonApiResponse signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.signUpCompany(companySignUpRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @ApiLogin
    @PostMapping("/login")
    public CommonApiResponse loginCompany(@Valid @RequestBody CompanyLoginRequestDto companyLoginRequestDto, HttpServletResponse response) {
        CommonApiResponse commonApiResponse = companyService.loginCompany(companyLoginRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    @GetMapping("/post/main")
    public CommonApiResponse postForMainPage() {
        return companyService.postForMainPage();
    }

    /**
     * Register post form api response.
     * @param session the session
     * @return the common api response
     * @author wjdals3936
     */
    @PostMapping("/post/common-data")
    public CommonApiResponse registerPostForm(HttpSession session){
        Long companyId = (Long) session.getAttribute("companyId");
        log.debug("companyId: "+ companyId);
//        Long companyId = 4L; // 테스트용 코드
        PostCommonDataResponseDto responseDto = companyService.getCompanyFaqsByCompanyId(companyId);
        System.out.println("========responseDto=========> " + responseDto);
        return new SuccessApiResponse<>(HttpStatus.OK.value(), "SUCCESS", responseDto);
    }
}