package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;

public interface CompanyService {
    /**
     * Sign up company api response.
     * @param companySignUpRequestDto the company sign up request dto
     * @return the api response
     * @author Kade-jeon
     */
    public ApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);

    /**
     * Gets company faqs by company id.
     * @param companyId the company id
     * @return the company faqs by company id
     * @author wjdals3936
     */
    public PostCommonDataResponseDto getCompanyFaqsByCompanyId(Long companyId);

}
