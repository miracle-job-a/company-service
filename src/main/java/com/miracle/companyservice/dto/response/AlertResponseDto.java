package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@ToString
@EqualsAndHashCode
public class AlertResponseDto {

    private final Long id;
    private final Long companyId;
    private final PostType postType;
    private final String title;
    private final String name;
    //기업정보에서 가져와야함
    private final Set<Long> jobIdSet;
    private final Integer career;

    private final String testStartDate;
    private final String testEndDate;

    public AlertResponseDto(Post post, String name) {
        this.id = post.getId();
        this.companyId = post.getCompanyId();
        this.postType = post.getPostType();
        this.title = post.getTitle();
        this.name = name;
        this.jobIdSet = post.getJobIdSet();
        this.career = post.getCareer();
        this.testStartDate = formatDate(post.getTestStartDate());
        this.testEndDate = formatDate(post.getTestEndDate());
    }

    public AlertResponseDto() {
        this.id = null;
        this.companyId = null;
        this.postType = null;
        this.title = null;
        this.name = null;
        this.jobIdSet = null;
        this.career = null;
        this.testStartDate = null;
        this.testEndDate = null;
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
