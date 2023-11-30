package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Post;
import com.miracle.companyservice.entity.PostType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@ToString
@Getter
@EqualsAndHashCode
public class MainPagePostsResponseDto {

    //postId
    private final Long id;

    private final Long companyId;

    private final PostType postType;

    private final String title;

    //기업정보에서 가져와야함
    private final String photo;

    private final String endDate;

    private final String workAddress;

    private final Set<Long> jobIdSet;

    private final int career;


    public MainPagePostsResponseDto(Post post, String photo) {
        this.id = post.getId();
        this.companyId = post.getCompanyId();
        this.postType = post.getPostType();
        this.title = post.getTitle();
        this.photo = photo;
        this.endDate = formatDate(post.getEndDate());
        this.workAddress = post.getWorkAddress();
        this.jobIdSet = post.getJobIdSet();
        this.career = post.getCareer();
    }

    private String formatDate(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

}
