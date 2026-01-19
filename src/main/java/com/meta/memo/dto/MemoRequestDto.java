package com.meta.memo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class MemoRequestDto {
    private Long id;

    @JsonProperty("username")
    private String userName;

    private String contents;
}
