package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;

public interface CompanyService {
    public ApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);
}
