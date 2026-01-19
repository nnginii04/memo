package com.meta.memo.dto;

import com.meta.memo.domain.Memo;
import lombok.Getter;
import lombok.Setter;

@Getter
public class MemoResponseDto {
    private Long id;
    private String userName;
    private String contents;

    public MemoResponseDto(Memo newMemo) {
        this.id =  newMemo.getId();
        this.userName = newMemo.getUsername();
        this.contents = newMemo.getContents();
    }

    public MemoResponseDto(Long id, String username, String contents) {
    }
}
