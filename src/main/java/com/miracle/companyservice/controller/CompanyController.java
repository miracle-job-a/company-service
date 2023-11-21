package com.miracle.companyservice.controller;


import com.miracle.companyservice.controller.swagger.ApiCheckBno;
import com.miracle.companyservice.controller.swagger.ApiCheckEmail;
import com.miracle.companyservice.controller.swagger.ApiLogin;
import com.miracle.companyservice.controller.swagger.ApiSignUp;
import com.miracle.companyservice.dto.request.*;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
    public CommonApiResponse checkEmail(@Valid @RequestBody CompanyCheckEmailRequestDto companyCheckEmailRequestDto) {
        return companyService.checkEmailDuplicated(companyCheckEmailRequestDto.getEmail());
    }

    @ApiCheckBno
    @PostMapping("/bno")
    public CommonApiResponse checkBno(@Valid @RequestBody CompanyCheckBnoRequestDto companyCheckBnoRequestDto) {
        return companyService.checkBnoStatus(companyCheckBnoRequestDto.getBno());
    }

    @ApiSignUp
    @PostMapping("/signup")
    public CommonApiResponse signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto) {
        return companyService.signUpCompany(companySignUpRequestDto);
    }

    @ApiLogin
    @PostMapping("/login")
    public CommonApiResponse loginCompany(@Valid @RequestBody CompanyLoginRequestDto companyLoginRequestDto) {
        return companyService.loginCompany(companyLoginRequestDto);
    }


    /**
     * Post common data common api response.
     *
     * @param companyId the company id
     * @param response  the response
     * @return the common api response
     * @author wjdals3936
     */
    @PostMapping("{companyId}/company-faq")
    public CommonApiResponse findCompanyFaq(@PathVariable Long companyId, HttpServletResponse response) {
        log.debug("companyId : {} ", companyId);
        CommonApiResponse commonApiResponse = companyService.getCompanyFaqsByCompanyId(companyId);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }

    /**
     * Post registration common api response.
     *
     * @param postRequestDto the post request dto
     * @param response       the response
     * @return the common api response
     * @author wjdals3936
     */
    @PostMapping("post/register")
    public CommonApiResponse registerPost(@RequestBody PostRequestDto postRequestDto, HttpServletResponse response){
        log.info("postRequestDto: {}", postRequestDto);
        CommonApiResponse commonApiResponse = companyService.savePost(postRequestDto);
        response.setStatus(commonApiResponse.getHttpStatus());
        return commonApiResponse;
    }
}