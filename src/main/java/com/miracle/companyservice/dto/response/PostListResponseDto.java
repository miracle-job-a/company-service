package com.miracle.companyservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public interface PostListResponseDto {
    Long getId();
    String getWorkAddress();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getEndDate();
    String getTitle();
    Integer getCareer();
    Set<Long> getJobIdSet();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();
}