package com.example.ai2_diary.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Autowired
private OpenAIClient openAIClient;

public Diary saveDiary(Diary diary) {
    // GPT를 사용해 감정 분석 및 긍정 변환 수행한다.
    String prompt = "다음 텍스트의 감정을 분석하고 긍정적으로 변환하세요: " + diary.getOriginalContent();
    String gptResponse = openAIClient.getChatGPTResponse(prompt);

    diary.setPositiveContent(gptResponse);
    diary.setSentiment(gptResponse.contains("부정") ? "부정" : "긍정");

    return diaryRepository.save(diary);
}

    // DALL-E를 통해 이미지화한다.
public String generateDiaryImage(String diaryContent) {
    String prompt = "다음 내용을 바탕으로 그림을 생성하세요: " + diaryContent;
    return openAIClient.generateImage(prompt);
}

