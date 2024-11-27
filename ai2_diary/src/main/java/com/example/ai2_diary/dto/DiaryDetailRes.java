package com.example.ai2_diary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiaryDetailRes {

    private Long diaryId;
    private String title;
    private String content;
    private String imgUrl;
}
