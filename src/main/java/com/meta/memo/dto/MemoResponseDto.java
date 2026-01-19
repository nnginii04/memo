package com.meta.memo.dto;

import com.meta.memo.domain.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String userName;
    private String contents;

    public MemoResponseDto(Memo newMemo) {
        this.id =  newMemo.getId();
        this.userName = newMemo.getUsername();
        this.contents = newMemo.getContents();
    }
}
