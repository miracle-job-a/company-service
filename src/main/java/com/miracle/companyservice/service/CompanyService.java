package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.CompanyFaqRequestDto;
import com.miracle.companyservice.dto.request.CompanyLoginRequestDto;
import com.miracle.companyservice.dto.request.CompanySignUpRequestDto;
import com.miracle.companyservice.dto.request.PostRequestDto;
import com.miracle.companyservice.dto.response.CommonApiResponse;

public interface CompanyService {

    /**
     * @author kade
     * @param id
     * @param email
     * @param bno
     * @return Boolean
     * 기업 회원 정보를 인증하는 메서드 -> 인터셉터에서 사용한다.
     */
    Boolean companyValidation(Long id, String email, String bno);

    /**
     * @author Kade
     * @param companySignUpRequestDto
     * @return Boolean
     * 기업회원의 회원가입 요청을 처리하는 메서드
     */
    CommonApiResponse signUpCompany(CompanySignUpRequestDto companySignUpRequestDto);

    /**
     * @author kade
     * @param companyLoginRequestDto
     * @return Boolean
     * 기업회원의 로그인 요청을 처리하는 메서드
     */
    CommonApiResponse loginCompany(CompanyLoginRequestDto companyLoginRequestDto);

    /**
     * @author kade
     * @param email
     * @return Boolean
     * 기업회원 가입 시, 이메일 중복여부 요청 처리하는 메서드
     */
    CommonApiResponse checkEmailDuplicated (String email);

    /**
     * @author kade
     * @param bno
     * @return Boolean
     * 기업회원 가입 시, 사업자 번호 확인 요청 처리하는 메서드
     */
    CommonApiResponse checkBnoStatus(String bno);

    /**
     * @author kade
     * @return Map<String, Object>
     * 게스트, 유저 메인페이지에 노출될 최근 공고 3건, 마감임박 공고 3건 반환하는 메서드
     * newest -> 최신 공고(MainPagePostsResponseDto)
     * deadline -> 마감임박 공고(MainPagePostsResponseDto)
     */
    CommonApiResponse postForMainPage();

    /**
     * @author kade
     * @param companyFaqRequestDto
     * @return Long
     * FAQ를 등록하는 메서드. 성공 시, faqId를 반환합니다.
     */
    CommonApiResponse addFaq(CompanyFaqRequestDto companyFaqRequestDto);

    /**
     * @author kade
     * @param companyId
     * @param faqId
     * @return Boolean
     * FAQ를 삭제하는 메서드
     */
    CommonApiResponse deleteFaq(Long companyId, Long faqId);

    /**
     * @author kade
     * @param companyId
     * @return List<CompanyFaqResponseDto>
     * 해당 기업의 전체 FAQ를 조회하는 메서드
     */
    CommonApiResponse getFaq(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @param postId
     * @return List<QuestionResponseDto>
     * 공고의 자기소개서 질문을 반환하는 메서드
     */
    CommonApiResponse returnQuestions(Long companyId, Long postId);

    /**
     * @author kade
     * @param companyId
     * @return Map<String, Long>
     * 기업 메인 페이지에 노출될 공고수를 반환하는 api
     * countAllPosts -> 전체 공고 수
     * countEndedPosts -> 진행 중 공고수
     * countOnGoing -> 마감된 공고수
     */
    CommonApiResponse getCountPosts(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @param postId
     * @return Boolean
     * 공고 마감 처리 시, 공고를 마감처리하는 API
     */
    CommonApiResponse changeToClose(Long companyId, Long postId);

    /**
     * Gets company faqs by company id.
     *
     * @param companyId the company id
     * @return the company faqs by company id
     * @author wjdals3936  공고 생성 및 상세 조회 시, 해당 기업 정보, FAQ 데이터를 반환하는 메서드
     */
    public CommonApiResponse getCompanyFaqsByCompanyId(Long companyId);

    /**
     * Register original post common api response.
     *
     * @param postRequestDto the post dto
     * @return the common api response
     * @author wjdals3936  일반 공고 등록 시, 공고 데이터를 저장하는 메서드
     */
    public CommonApiResponse savePost(PostRequestDto postRequestDto);

    public CommonApiResponse findPostById(Long postId);
}
