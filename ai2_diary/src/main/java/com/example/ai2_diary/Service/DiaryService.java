package com.example.ai2_diary.Service;

import com.example.ai2_diary.diary.Diary;
import com.example.ai2_diary.dto.CreateDiaryReq;
import com.example.ai2_diary.dto.DiaryDetailRes;
import com.example.ai2_diary.dto.DiaryRes;
import com.example.ai2_diary.repository.DiaryRepository;
import com.example.ai2_diary.s3.application.FileService;
import com.example.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryService {

//    private final OpenAIClient openAIClient;
    private final FileService fileService;
    private final DiaryRepository diaryRepository;

    @Transactional
    public void createDiary(CreateDiaryReq createDiaryReq) {
        Diary diary = Diary.builder()
                .title(createDiaryReq.getTitle())
                .content(createDiaryReq.getContent())
                .imgUrl(createDiaryReq.getImgUrl())
                .build();
        diaryRepository.save(diary);
    }

    public SuccessResponse<List<DiaryRes>> findDiaryList() {
        List<Diary> diaryList = diaryRepository.findAll();
        List<DiaryRes> diaryResList = diaryList.stream().map(diary -> DiaryRes.builder()
                        .diaryId(diary.getId())
                        .title(diary.getTitle())
                        .imgUrl(diary.getImgUrl())
                        .build())
                .collect(Collectors.toList());
        return SuccessResponse.of(diaryResList);
    }

    public SuccessResponse<DiaryDetailRes> findDiary(Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 아이디의 일기가 없습니다. " + diaryId));
        DiaryDetailRes diaryDetailRes = DiaryDetailRes.builder()
                .diaryId(diary.getId())
                .title(diary.getTitle())
                .content(diary.getContent())
                .imgUrl(diary.getImgUrl())
                .build();
        return SuccessResponse.of(diaryDetailRes);
    }

//    public Diary saveDiary(Diary diary) {
//        // GPT를 사용해 감정 분석 및 긍정 변환 수행한다.
//        String prompt = "다음 텍스트의 감정을 분석하고 긍정적으로 변환하세요: " + diary.getOriginalContent();
//        String gptResponse = openAIClient.getChatGPTResponse(prompt);
//
//        diary.setPositiveContent(gptResponse);
//        diary.setSentiment(gptResponse.contains("부정") ? "부정" : "긍정");
//
//        return diaryRepository.save(diary);
//    }

    // DALL-E를 통해 이미지화한다.
//    public String generateDiaryImage(String diaryContent) {
//        String prompt = "다음 내용을 바탕으로 그림을 생성하세요: " + diaryContent;
//        return openAIClient.generateImage(prompt);
//    }
}
