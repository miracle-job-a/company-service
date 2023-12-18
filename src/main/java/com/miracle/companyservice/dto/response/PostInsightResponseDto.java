package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@ToString
public class PostInsightResponseDto {
    private final PostType postType;
    private final String createdAt;

    public PostInsightResponseDto(Post post) {
        this.postType = post.getPostType();
        this.createdAt = formatDate(post.getCreatedAt());
    }

    public PostInsightResponseDto() {
        this.postType = null;
        this.createdAt = null;
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

}