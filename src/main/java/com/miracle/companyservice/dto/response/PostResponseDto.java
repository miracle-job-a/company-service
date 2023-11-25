package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

@Getter
@ToString
public class PostResponseDto{

    private final PostType postType;
    private final String title;
    private final String endDate;
    private final String tool;
    private final String workAddress;
    private final String mainTask;
    private final String workCondition;
    private final String qualification;
    private final String benefit;
    private final String specialSkill;
    private final String process;
    private final String notice;
    private final String career;
    private final Boolean closed;
    private final Set<Long> jobIdSet;
    private final Set<Long> stackIdSet;
    private final List<QuestionResponseDto> questionList;
    private final String testStartDate;
    private final String testEndDate;

    @Builder
    public PostResponseDto(Post post, List<QuestionResponseDto> questionList){
        this.postType = post.getPostType();
        this.title = post.getTitle();
        this.endDate = formatDate(post.getEndDate(), "yyyy-MM-dd");
        this.tool = post.getTool();
        this.workAddress = post.getWorkAddress();
        this.mainTask = post.getMainTask();
        this.workCondition = post.getWorkCondition();
        this.qualification = post.getQualification();
        this.benefit = post.getBenefit();
        this.specialSkill = post.getSpecialSkill();
        this.process = post.getProcess();
        this.notice = post.getNotice();
        this.career = post.getBenefit();
        this.closed = post.isClosed();
        this.jobIdSet = post.getJobIdSet();
        this.stackIdSet = post.getStackIdSet();
        this.questionList = questionList;
        this.testStartDate = formatDate(post.getTestStartDate(), "yyyy-MM-dd'T'HH:mm:ss");
        this.testEndDate = formatDate(post.getTestEndDate(), "yyyy-MM-dd'T'HH:mm:ss");
    }

    private String formatDate(LocalDateTime date, String format) {
        if (date == null) {
            return "null";
        }
        return date.format(DateTimeFormatter.ofPattern(format));
    }
}
