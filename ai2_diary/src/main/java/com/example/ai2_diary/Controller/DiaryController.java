package com.example.ai2_diary.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@GetMapping("/generate-image/{id}")
public String generateImage(@PathVariable Long id) {
    Diary diary = diaryService.findDiaryById(id);
    return diaryService.generateDiaryImage(diary.getPositiveContent());
}

