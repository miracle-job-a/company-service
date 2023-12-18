package com.miracle.companyservice.dto.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SearchResultResponseDto {
    private final List<PostListResponseDto> postList;
    private final List<CompanyListResponseDto> companyList;

    public SearchResultResponseDto(List<PostListResponseDto> postList, List<CompanyListResponseDto> companyList) {
        this.postList = postList;
        this.companyList = companyList;
    }
}
