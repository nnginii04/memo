package com.meta.memo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meta.memo.domain.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;

    @JsonProperty("username")
    private String userName;

    private String contents;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.userName = memo.getUsername();
        this.contents = memo.getContents();
    }

    public MemoResponseDto(Long id, String username, String contents) {
        this.id = id;
        this.userName = username;
        this.contents = contents;
    }
}
