package com.miracle.companyservice.service;

import com.miracle.companyservice.dto.request.*;
import com.miracle.companyservice.dto.response.CommonApiResponse;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * The interface Company service.
 */
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
     * @param companyId
     * @param companyFaqRequestDto
     * @return Long
     * FAQ를 등록하는 메서드. 성공 시, faqId를 반환합니다
     */
    CommonApiResponse addFaq(long companyId, CompanyFaqRequestDto companyFaqRequestDto);

    /**
     * @author kade
     * @param companyId
     * @param faqId
     * @return Boolean
     * FAQ를 삭제하는 메서드
     */
    CommonApiResponse deleteFaq(Long companyId, Long faqId);

    /**
     * @param companyId
     * @return List<CompanyFaqResponseDto>
     * 해당 기업의 전체 FAQ를 조회하는 메서드
     */
    CommonApiResponse getFaq(Long companyId);

    /**
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
     * @author kade
     * @param companyId
     * @param strNum
     * @param endNum
     * @param sort
     * @return List<Page<ManagePostsResponseDto>>
     * sort = latest   : 기업의 공고관리 페이지에 보이는 공고 리스트를 최신순 정렬합니다. (디폴트)
     * sort = deadline : 기업의 공고관리 페이지에 보이는 공고 리스트를 마감임박순으로 정렬합니다.
     * sort = deadline : 기업의 공고관리 페이지에서 마감된 공고만 정렬하여 보여줍니다. (마감된 공고만 보기)
     * sort = deadline : 기업의 공고관리 페이지에서 진행중 공고만 정렬하여 보여줍니다. (진행중 공고만 보기)
     */
    CommonApiResponse managePost(Long companyId, int strNum, int endNum, String sort);


    /**
     * @author kade
     * @param strNum 시작페이지
     * @param endNum 종료페이지
     * @param conditionalSearchPostRequestDto
     * @return List<ConditionalSearchPostResponseDto>
     * 공고의 조건검색을 처리하는 메서드
     */
    CommonApiResponse conditionalSearch(int strNum, int endNum, ConditionalSearchPostRequestDto conditionalSearchPostRequestDto);

    /**
     * @author kade
     * @param strNum
     * @param endNum
     * @param today
     * @return List<CompanyListForAdminResponseDto>
     * 관리자가 기업회원 목록을 조회하여 최근 가입 순 정렬하여 반환
     * 파라미터 today = false -> 전체 검색
     * 파라미터 today = true -> 오늘 가입한 회원만 검색
     */
    CommonApiResponse getCompanyList(int strNum, int endNum, boolean today);

    /**
     * @author kade
     * @param companyId
     * @return boolean
     * 기업 회원 탈퇴 처리 API
     */
    CommonApiResponse quitCompany(Long companyId);

    /**
     * @author kade
     * @param companyId
     * @return boolean
     * 기업 회원 승인 처리 API
     */
    CommonApiResponse approveCompany(Long companyId);

    /**
     * @author kade
     * endDate(공고마감일)이 지나면 자동으로 마감처리하는 메서드
     */
    void closePostBySchedule();

    /**
     * @author kade
     * @return List<AlertResponseDto>
     * 테스트 시작전 알림을 위한 정보 반환해주는 API
     */
    CommonApiResponse postsInfoForTestAlert();

    /**
     * @author kade
     * @param companyId
     * @return Boolean
     * 기업이 정상적으로 공고 등록이 가능한 상태인지 반환하는 API
     * 사업자번호 만료여부와 가입승인여부를 확인한 후 리턴한다.
     */
    CommonApiResponse checkPostAuthority(Long companyId);

    /**
     * @author wjdals3936
     * @param companyId the company id
     * @return the company faqs by company id
     * 공고 생성 및 상세 조회 시, 해당 기업 정보, FAQ 데이터를 반환하는 메서드
     */
    public CommonApiResponse getCompanyInfoAndFaqs(Long companyId);

    /**
     * @author wjdals3936
     * @param postRequestDto
     * @param companyId
     * @return the common api response
     * 일반 공고 등록 시, 공고 데이터를 저장하는 메서드
     */
    public CommonApiResponse savePost(Long companyId, PostRequestDto postRequestDto);

    /**
     * @author wjdals3936
     * @param companyId the company id
     * @param postId the post id
     * @return the common api response
     * 공고 상세보기 및 공고 수정 페이지에 해당 공고 데이터 반환 메서드
     */
    public CommonApiResponse findPost(Long companyId, Long postId);

    /**
     * @author wjdals3936
     * @param postRequestDto the post request dto
     * @return the common api response
     * 공고 수정 메서드
     */
    public CommonApiResponse modifyPost(Long companyId, Long postId, PostRequestDto postRequestDto);

    /**
     * @author wjdals3936
     * @param companyId the company id
     * @param postId the post id
     * @return the common api response
     * 공고 삭제 메서드
     */
    public CommonApiResponse deletePost(Long companyId, Long postId);

    /**
     * @author wjdals3936
     * @param companyId the company id
     * @return the common api response
     * 기업 상세페이지 정보 반환 메서드
     */
    public CommonApiResponse findCompany(Long companyId);

    /**
     * @author wjdals3936
     * @param keyword the keyword
     * @return the common api response
     * 검색 키워드에 대해 해당되는 공고/기업 데이터 반환 메서드
     */
    public CommonApiResponse findPostAndCompany(String keyword, int strNum, int endNum);

    /**
     * @author wjdals3936
     * @param companyId
     * @param requestDto
     * @return the common api response
     * 회원 기업 정보 수정 메서드
     */
    public CommonApiResponse modifyCompanyInfo(Long companyId, CompanyInfoRequestDto requestDto);

    /**
     * @author wjdals3936
     * @param companyLoginRequestDto
     * @return the common api response
     * 기업회원 정보 수정을 위한 이메일/비밀번호 확인 처리 메서드
     */
    CommonApiResponse userCheck(Long companyId, CompanyLoginRequestDto companyLoginRequestDto);

    /**
     * @author wjdals3936
     * @param idSet
     * @return the common api response
     * 기업 정보 (id/name) 반환 메서드
     */
    CommonApiResponse getCompanyName(Set<Long> idSet);

    /**
     * @author wjdals3936
     * @return Map<String, Long>
     * countNormalPosts -> 진행 중인 일반 공고 수
     * countMZPosts -> 진행 중인 MZ 공고 수
     * 관리자 인사이트에 노출될 공고 수를 반환하는 메서드
     */
    CommonApiResponse getCountWholePosts();

    /**
     * @author wjdals3936
     * @param postId
     * @return Boolean
     * 마감/삭제된 공고 지원을 막기 위한 공고 마감/삭제 여부를 반환하는 메서드 
     */
    CommonApiResponse checkPostStatus(Long postId);

    /**
     * @author wjdals3936
     * @return List<PostInsightResponseDto>
     * 관리자 인사이트에 노출될 금일 등록된 공고 리스트를 반환하는 메서드
     */
    CommonApiResponse getTodayPosts(int year, int month);

    /**
     * @author wjdals3936
     * @return List<PostInsightResponseDto>
     * 관리자 인사이트에 노출될 현재 모집중인 공고의 스택/직무 리스트를 반환하는 메서드
     */
    CommonApiResponse getAllJobsAndStacks(Long companyId);
}
