package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.ApiResponse;

public interface CompanyService {
    /**
     * @author kade
     * @param companySignUpRequestDto
     * @return ApiResponse
     * 기업회원의 회원가입 요청을 처리하는 메서드, 아이디 / 사업자번호 중복일 경우 에러가 발생한다.
     */
    ApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);

    /**
     * @author kade
     * @param companyLoginRequestDto
     * @return ApiResponse
     * @data
     */
    ApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto);

    ApiResponse checkEmailDuplicated (String email);
    ApiResponse checkBnoStatus(String bno);

}
