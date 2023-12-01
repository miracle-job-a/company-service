package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@ToString
public class ConditionalSearchPostResponseDto {

    private final Long id;
    private final String title;
    private final PostType postType;
    private final String createdAt;
    private final String endDate;
    private final Boolean closed;

    public ConditionalSearchPostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.postType = post.getPostType();
        this.createdAt = formatDate(post.getCreatedAt());
        this.endDate = formatDate(post.getEndDate());
        this.closed = post.isClosed();
    }

    public ConditionalSearchPostResponseDto() {
        this.id = null;
        this.title = null;
        this.postType = null;
        this.createdAt = null;
        this.endDate = null;
        this.closed = null;
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }
}
