package com.miracle.companyservice.controller;


import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.SuccessApiResponse;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * Instantiates a new Company controller.
     * @param companyServiceImpl the company service
     */
    @Autowired
    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyServiceImpl;
    }

    /**
     * Sign up company api response.
     * @param companySignUpRequestDto the company sign up request dto
     * @param request                 the request
     * @return the api response
     * @author Kade-jeon
     */
    @PostMapping("/signup")
    public ApiResponse signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto, HttpServletRequest request) {
        return companyService.signUpCompany(companySignUpRequestDto);
    }

    /**
     * Register post form api response.
     * @param session the session
     * @return the api response
     * @author wjdals3936
     */
    @PostMapping("/post/registration-form")
    public ApiResponse registerPostForm(HttpSession session){
        Long companyId = (Long) session.getAttribute("companyId");
        log.debug("companyId: "+ companyId);
//        Long companyId = 4L; // 테스트용 코드
        PostCommonDataResponseDto responseDto = companyService.getCompanyFaqsByCompanyId(companyId);
        System.out.println("========responseDto=========> " + responseDto);
        return new SuccessApiResponse<>(HttpStatus.OK.value(), "SUCCESS", responseDto);
    }
}