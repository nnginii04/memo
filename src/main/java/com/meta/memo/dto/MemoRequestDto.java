package com.meta.memo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter

public class MemoRequestDto {
    private Long id;
    private String userName;
    private String contents;
}
