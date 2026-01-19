package com.meta.memo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.meta.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoResponseDto {
    private Long id;

    @JsonProperty("username")  // userName 필드를 JSON에서는 username으로 내보냄
    private String userName;

    private String contents;

    public MemoResponseDto(Memo newMemo) {
        this.id = newMemo.getId();
        this.userName = newMemo.getUsername();
        this.contents = newMemo.getContents();
    }
}
