package com.miracle.companyservice.dto.request;

import com.sun.istack.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@ToString
public class PostIdRequestDto {

    @Schema(
            description = "post id Set은 not null",
            required = true,
            example = "[1,2,3]"
    )
    @NotNull
    private final Set<Long> postId;

    public PostIdRequestDto(Set<Long> postId) {
        this.postId = postId;
    }

    public PostIdRequestDto() {
        this.postId = null;
    }
}
