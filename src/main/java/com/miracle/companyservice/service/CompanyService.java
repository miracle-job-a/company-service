package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyInfoReqeustDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.response.PostCommonDataResponseDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;

public interface CompanyService {

    public Boolean companyValidation(Long id, String email, String bno);
    /**
     * @author kade
     * @param companySignUpRequestDto
     * @return CommonApiResponse
     * 기업회원의 회원가입 요청을 처리하는 메서드
     *
     */
    CommonApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);

    /**
     * @author kade
     * @param companyLoginRequestDto
     * @return CommonApiResponse
     * 기업회원의 로그인 요청을 처리하는 메서드
     */
    CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto);

    /**
     * @author kade
     * @param email
     * @return CommonApiResponse
     * 기업회원 가입 시, 이메일 중복여부 요청 처리하는 메서드
     */
    CommonApiResponse checkEmailDuplicated (String email);

    /**
     * @author kade
     * @param bno
     * @return CommonApiResponse
     * 기업회원 가입 시, 사업자 번호 확인 요청 처리하는 메서드
     */
    CommonApiResponse checkBnoStatus(String bno);

    /**
     * @author kade
     * @return
     * 게스트, 유저 메인페이지에 노출될 최근 공고 3건, 마감임박 공고 3건 반환하는 메서드
     */
    CommonApiResponse postForMainPage();






    /**
     * Gets company faqs by company id.
     * @param companyId the company id
     * @return the company faqs by company id
     * @author wjdals3936
     * 공고 생성 및 상세 조회 시, 해당 기업 정보, FAQ 데이터를 반환하는 메서드
     */
    public CommonApiResponse getCompanyFaqsByCompanyId(Long companyId);

}
