package com.miracle.companyservice.controller;


import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.exception.UnauthorizedTokenException;
import com.miracle.companyservice.service.CompanyService;
import com.miracle.companyservice.service.CompanyServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/v1/company")
public class CompanyController {

    private final CompanyService companyService;
    private static final String privateKey = "TkwkdsladkdlrhdnjfrmqdhodlfjgrpaksgdlwnjTdjdy";

    @Autowired
    public CompanyController(CompanyServiceImpl companyServiceImpl) {
        this.companyService = companyServiceImpl;
    }

    @PostMapping("/signup")
    public ApiResponse signUpCompany(@Valid @RequestBody CompanySignUpRequestDto companySignUpRequestDto,
                                     HttpServletRequest request) {
        tokenCheck(request);
        return companyService.signUpCompany(companySignUpRequestDto);
    }

    private void tokenCheck(HttpServletRequest request) {
        String id = request.getHeader("sessionId")+ privateKey;
        int miracle = id.hashCode();
        if (miracle == request.getIntHeader("miracle")) {
            throw new UnauthorizedTokenException("토큰 값이 일치하지 않습니다.");
        }
    }
}
