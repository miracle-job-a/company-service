package com.miracle.companyservice.dto.response;

import com.miracle.companyservice.entity.Company;
import com.miracle.companyservice.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;


@Getter
@ToString
public class JobStackInsightResponseDto {
    private final Long id;
    private final Set<Long> jobIdSet;
    private final Set<Long> stackIdSet;

    @Builder
    public JobStackInsightResponseDto(Post post) {
        this.id = post.getId();
        this.jobIdSet = post.getJobIdSet();
        this.stackIdSet = post.getStackIdSet();
    }

    public JobStackInsightResponseDto() {
        this.id = 0L;
        this.jobIdSet = null;
        this.stackIdSet = null;
    }
}