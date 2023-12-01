package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.entity.PostType;
import com.miracle.companyservice.entity.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
public class PostRequestDto {
    @Schema(
            description = "공고 타입 오류. 공고 타입은 NORMAL, MZ 둘 중 하나여야 함",
            required = true,
            example = "NORMAL"
    )
    @NotBlank(message = "400_19:공고 타입 값이 없습니다.")
    @Pattern(regexp = "NORMAL|MZ", message = "400_19:공고 타입은 NORMAL 또는 MZ 중 하나여야 합니다.")
    private final PostType postType;

    @Schema(
            description = "공고 제목 오류. 반드시 제목이 입력되어야 함",
            required = true,
            example = "백엔드 개발자 신입 채용"
    )
    @NotBlank(message = "400_15:공고 제목 값이 없습니다.")
    private final String title;

    @Schema(
            description = "경력 오류. 경력은 0이거나 양수여야 함",
            required = true,
            example = "3"
    )
    @PositiveOrZero(message = "400_21:경력 값은 0을 포함한 양수를 허용합니다.")
    @NotBlank(message = "400_21:경력 값이 없습니다.")
    private final int career;

    @Schema(
            description = "마감일 오류. LocalDateTime 형식이여야 함",
            required = true,
            example = "2023-12-21T00:00:00"
    )
    @NotBlank(message = "400_16:마감일 값이 없습니다.")
    @Future(message = "400_16:현재 날짜보다 미래의 날짜를 설정해야 합니다.")
    private final LocalDateTime endDate;

    @NotBlank(message = "400_22:주요 업무 값이 없습니다.")
    private final String mainTask;

    @NotBlank(message = "400_23:근무 조건 값이 없습니다.")
    private final String workCondition;
    private final String qualification;

    @Schema(
            description = "개발툴 오류.",
            required = true,
            example = "Intellij, EC2, Docker"
    )
    @NotBlank(message = "400_17:개발툴 값이 없습니다.")
    private final String tool;
    private final String benefit;

    @NotBlank(message = "400_24:채용절차 값이 없습니다.")
    private final String process;
    private final String notice;
    private final String specialSkill;

    @Schema(
            description = "근무지 오류.",
            required = true,
            example = "경기도 평택시 "
    )
    @NotBlank(message = "400_18:근무지 값이 없습니다.")
    private final String workAddress;

    @Valid
    private final List<QuestionRequestDto> questionList;

    @Schema(
            description = "직무 ID Set 오류.",
            required = true,
            example = "[1L, 2L, 3L]"
    )
    @NotEmpty(message = "400_11:직무 ID 집합 값이 없습니다.")
    private final Set<Long> jobIdSet;

    @Schema(
            description = "스택 ID Set 오류.",
            required = true,
            example = "[4L, 5L, 6L]"
    )
    @NotEmpty(message = "400_11:스택 ID 집합 값이 없습니다.")
    private final Set<Long> stackIdSet;

    @Schema(
            description = "테스트 시작일 오류.",
            required = true,
            example = "2023-12-21T00:00:00"
    )
    @Future(message = "400_20:현재 날짜보다 미래의 날짜를 설정해야 합니다.")
    private final LocalDateTime testStartDate;

    @Schema(
            description = "테스트 마감일 오류.",
            required = true,
            example = "2023-12-31T00:00:00"
    )
    @Future(message = "400_20:현재 날짜보다 미래의 날짜를 설정해야 합니다.")
    private final LocalDateTime testEndDate;

    @Builder
    public PostRequestDto(PostType postType, String title, int career, LocalDateTime endDate, String mainTask, String workCondition, String qualification, String tool, String benefit, String process, String notice, String specialSkill, String workAddress, List<QuestionRequestDto> questionList, Set<Long> jobIdSet, Set<Long> stackIdSet, LocalDateTime testStartDate, LocalDateTime testEndDate) {
        this.postType = postType;
        this.title = title;
        this.career = career;
        this.endDate = endDate;
        this.mainTask = mainTask;
        this.workCondition = workCondition;
        this.qualification = qualification;
        this.tool = tool;
        this.benefit = benefit;
        this.process = process;
        this.notice = notice;
        this.specialSkill = specialSkill;
        this.workAddress = workAddress;
        this.questionList = questionList;
        this.jobIdSet = jobIdSet;
        this.stackIdSet = stackIdSet;
        this.testStartDate = testStartDate;
        this.testEndDate = testEndDate;
    }

    public PostRequestDto() {
        this.postType = null;
        this.title = null;
        this.career = 0;
        this.endDate = null;
        this.mainTask = null;
        this.workCondition = null;
        this.qualification = null;
        this.tool = null;
        this.benefit = null;
        this.process = null;
        this.notice = null;
        this.specialSkill = null;
        this.workAddress = null;
        this.questionList = null;
        this.jobIdSet = null;
        this.stackIdSet = null;
        this.testStartDate = null;
        this.testEndDate = null;
    }
}
