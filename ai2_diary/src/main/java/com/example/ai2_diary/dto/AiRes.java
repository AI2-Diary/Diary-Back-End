package com.example.ai2_diary.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AiRes {

    private String content;
    private String imgUrl;
}
