package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.entity.PostType;
import com.miracle.companyservice.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
public class PostRequestDto {
    private final Long postId;
    private final Long companyId;
    private final PostType postType;
    private final String title;
    private final int career;
    private final LocalDateTime endDate;
    private final String mainTask;
    private final String workCondition;
    private final String qualification;
    private final String tool;
    private final String benefit;
    private final String process;
    private final String notice;
    private final String specialSkill;
    private final String workAddress;
    private final List<QuestionRequestDto> questionList;
    private final Set<Long> jobIdSet;
    private final Set<Long> stackIdSet;
    private final LocalDateTime testStartDate;
    private final LocalDateTime testEndDate;

    @Builder
    public PostRequestDto(Long postId, Long companyId, PostType postType, String title, int career, LocalDateTime endDate, String mainTask, String workCondition, String qualification, String tool, String benefit, String process, String notice, String specialSkill, String workAddress, List<QuestionRequestDto> questionList, Set<Long> jobIdSet, Set<Long> stackIdSet, LocalDateTime testStartDate, LocalDateTime testEndDate) {
        this.postId = postId;
        this.companyId = companyId;
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
        this.postId = null;
        this.companyId = null;
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
