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
public class ManagePostsResponseDto {

    private final Long id;
    private final PostType postType;
    private final String title;
    private final Set<Long> jobIdSet;
    private final String createdAt;
    private final String endDate;
    private final Boolean closed;
    public ManagePostsResponseDto(Long id, PostType postType, String title, Set<Long> jobIdSet, LocalDateTime createdAt, LocalDateTime endDate, Boolean closed) {
        this.id = id;
        this.postType = postType;
        this.title = title;
        this.jobIdSet = jobIdSet;
        this.createdAt = formatDate(createdAt);
        this.endDate = formatDate(endDate);
        this. closed = closed;
    }

    public ManagePostsResponseDto(Post post) {
        this.id = post.getId();
        this.postType = post.getPostType();
        this.title = post.getTitle();
        this.jobIdSet = post.getJobIdSet();
        this.createdAt = formatDate(post.getCreatedAt());
        this.endDate = formatDate(post.getEndDate());
        this. closed = post.isClosed();
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
