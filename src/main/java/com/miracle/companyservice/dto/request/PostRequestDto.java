package com.miracle.companyservice.dto.request;

import com.miracle.companyservice.entity.PostType;
import com.miracle.companyservice.entity.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@ToString
public class PostRequestDto {
    private Long companyId;
    private PostType postType;
    private String title;
    private int career;
    private LocalDateTime endDate;
    private String mainTask;
    private String workCondition;
    private String qualification;
    private String tool;
    private String benefit;
    private String process;
    private String notice;
    private String specialSkill;
    private String workAddress;
    private List<Question> questionList;
    private Set<Long> jobIdSet;
    private Set<Long> stackIdSet;

    @Builder
    public PostRequestDto(Long companyId, PostType postType,
                          String title, LocalDateTime endDate, String tool,
                          String workAddress, String mainTask, String workCondition,
                          String qualification, String benefit,
                          String specialSkill, String process,
                          String notice, int career,
                          List<Question> questionList, Set<Long> jobIdSet, Set<Long> stackIdSet) {
        this.companyId = companyId;
        this.postType = postType;
        this.title = title;
        this.endDate = endDate;
        this.tool = tool;
        this.workAddress = workAddress;
        this.mainTask = mainTask;
        this.workCondition = workCondition;
        this.qualification = qualification;
        this.benefit = benefit;
        this.specialSkill = specialSkill;
        this.process = process;
        this.notice = notice;
        this.career = career;
        this.questionList = questionList;
        this.jobIdSet = jobIdSet;
        this.stackIdSet = stackIdSet;
    }
}
