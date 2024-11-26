package com.example.ai2_diary.Controller;

import com.example.ai2_diary.Service.DiaryService;
import com.example.ai2_diary.dto.CreateDiaryReq;
import com.example.ai2_diary.dto.DiaryDetailRes;
import com.example.ai2_diary.dto.DiaryRes;
import com.example.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<Void> createDiary(@RequestBody CreateDiaryReq createDiaryReq) {
        diaryService.createDiary(createDiaryReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<DiaryRes>>> findDiaryList() {
        return ResponseEntity.ok(diaryService.findDiaryList());
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<SuccessResponse<DiaryDetailRes>> findDiary(@PathVariable("diaryId") Long diaryId) {
        return ResponseEntity.ok(diaryService.findDiary(diaryId));
    }

//    public String generateImage(@PathVariable Long id) {
//        Diary diary = diaryService.findDiaryById(id);
//        return diaryService.generateDiaryImage(diary.getPositiveContent());
//    }
}
