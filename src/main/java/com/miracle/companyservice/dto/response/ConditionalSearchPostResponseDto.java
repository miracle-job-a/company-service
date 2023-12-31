package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@ToString
public class ConditionalSearchPostResponseDto {

    private final Long id;
    private final Long companyId;
    private final String name;
    private final String title;
    private final PostType postType;
    private final String endDate;
    private final Boolean closed;
    private final String workAddress;
    private final Integer career;
    private final Set<Long> jobIdSet;
    private final String photo;
    public ConditionalSearchPostResponseDto(Post post, String companyName, String photo) {
        this.id = post.getId();
        this.companyId = post.getCompanyId();
        this.name = companyName;
        this.title = post.getTitle();
        this.postType = post.getPostType();
        this.endDate = formatDate(post.getEndDate());
        this.closed = post.isClosed();
        this.workAddress = post.getWorkAddress();
        this.career = post.getCareer();
        this.jobIdSet = post.getJobIdSet();
        this.photo = photo;
    }

    public ConditionalSearchPostResponseDto() {
        this.id = null;
        this.companyId = null;
        this.name = null;
        this.title = null;
        this.postType = null;
        this.endDate = null;
        this.closed = null;
        this.workAddress = null;
        this.career = null;
        this.jobIdSet = null;
        this.photo = null;
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
