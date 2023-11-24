package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyFaqRequestDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.request.PostRequestDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;

/**
 * The interface Company service.
 */
public interface CompanyService {

    /**
     * Company validation boolean.
     *
     * @param id    the id
     * @param email the email
     * @param bno   기업 회원 정보를 인증하는 메서드 -> 인터셉터에서 사용한다.
     * @return the boolean
     * @author kade
     */
    public Boolean companyValidation(Long id, String email, String bno);

    /**
     * Sign up company common api response.
     *
     * @param companySignUpRequestDto the company sign up request dto
     * @return CommonApiResponse 기업회원의 회원가입 요청을 처리하는 메서드
     * @author kade
     */
    CommonApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);

    /**
     * Login company common api response.
     *
     * @param companyLoginRequestDto the company login request dto
     * @return CommonApiResponse 기업회원의 로그인 요청을 처리하는 메서드
     * @author kade
     */
    CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto);

    /**
     * Check email duplicated common api response.
     *
     * @param email the email
     * @return CommonApiResponse 기업회원 가입 시, 이메일 중복여부 요청 처리하는 메서드
     * @author kade
     */
    CommonApiResponse checkEmailDuplicated (String email);

    /**
     * Check bno status common api response.
     *
     * @param bno the bno
     * @return CommonApiResponse 기업회원 가입 시, 사업자 번호 확인 요청 처리하는 메서드
     * @author kade
     */
    CommonApiResponse checkBnoStatus(String bno);

    /**
     * Post for main page common api response.
     *
     * @return 게스트, 유저 메인페이지에 노출될 최근 공고 3건, 마감임박 공고 3건 반환하는 메서드
     * @author kade
     */
    CommonApiResponse postForMainPage();

    /**
     * Add faq common api response.
     *
     * @param companyFaqRequestDto FAQ를 등록하는 메서드
     * @return the common api response
     * @author kade
     */
    CommonApiResponse addFaq(CompanyFaqRequestDto companyFaqRequestDto);

    /**
     * Delete faq common api response.
     *
     * @param compnayId the compnay id
     * @param faqId     FAQ를 삭제하는 메서드
     * @return the common api response
     * @author kade
     */
    CommonApiResponse deleteFaq(Long compnayId, Long faqId);

    /**
     * Gets faq.
     *
     * @param companyId 해당 기업의 전체 FAQ를 조회하는 메서드
     * @return the faq
     * @author kade
     */
    CommonApiResponse getFaq(Long companyId);

    /**
     * Return questions common api response.
     *
     * @param companyId the company id
     * @param postId    공고의 자기소개서 질문을 반환하는 메서드
     * @return the common api response
     * @author kade
     */
    CommonApiResponse returnQuestions(Long companyId, Long postId);

    /**
     * Gets company faqs by company id.
     *
     * @param companyId the company id
     * @return the company faqs by company id 공고 생성 및 상세 조회 시, 해당 기업 정보, FAQ 데이터를 반환하는 메서드
     * @author wjdals3936
     */
    public CommonApiResponse getCompanyFaqsByCompanyId(Long companyId);

    /**
     * Register original post common api response.
     * @author wjdals3936
     * @param postRequestDto the post dto
     * @return the common api response
     * 일반 공고 등록 시, 공고 데이터를 저장하는 메서드
     */
    public CommonApiResponse savePost(PostRequestDto postRequestDto);

    /**
     * Find post by id common api response.
     * @author wjdals3936
     * @param postId the post id
     * @return the common api response
     * 공고 상세보기 및 공고 수정 페이지에 해당 공고 데이터 반환 메서드
     */
    public CommonApiResponse findPostById(Long postId);

    /**
     * Modify post by id common api response.
     * @author wjdals3936
     * @param postRequestDto the post request dto
     * @return the common api response
     * 공고 수정 메서드
     */
    public CommonApiResponse modifyPostById(PostRequestDto postRequestDto);
}
