package com.miracle.companyservice.controller;


import com.miracle.companyservice.dto.api.BaseApi;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/signup")
    public BaseApi signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto) {
        return companyService.signUpCompany(companySignUpRequestDto);
    }
}
