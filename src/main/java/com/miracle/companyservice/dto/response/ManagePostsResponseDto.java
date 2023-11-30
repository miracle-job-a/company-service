package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.PostType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@ToString
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

    private String formatDate(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
