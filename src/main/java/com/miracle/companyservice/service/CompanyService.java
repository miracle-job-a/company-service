package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.api.BaseApi;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;

public interface CompanyService {
    public BaseApi signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);
}
