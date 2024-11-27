package com.example.ai2_diary.dto;

import lombok.Data;

@Data
public class CreateDiaryReq {

    private String title;
    private String content;
    private String imgUrl;
}
