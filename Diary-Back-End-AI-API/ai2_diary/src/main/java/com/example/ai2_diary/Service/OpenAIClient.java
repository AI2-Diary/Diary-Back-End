package com.example.ai2_diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OpenAIClient {

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public OpenAIClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // GPT API 호출
    public String getChatGPTResponse(String prompt) {
        String endpoint = apiUrl + "/chat/completions";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo"); // 원하는 모델 이름
        requestBody.put("messages", List.of(Map.of("role", "user", "content", prompt)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                if (body != null && body.containsKey("choices")) {
                    Map<String, Object> choice = (Map<String, Object>) ((List<?>) body.get("choices")).get(0);
                    return (String) ((Map<?, ?>) choice.get("message")).get("content");
                }
            }
            return "GPT 응답 없음";
        } catch (Exception e) {
            throw new RuntimeException("GPT 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public String generateImage(String prompt) {
        String endpoint = apiUrl + "/images/generations";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        // 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("n", 1);
        requestBody.put("size", "1024x1024"); // 원하는 이미지 크기

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    endpoint,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            // 응답 처리
            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> body = response.getBody();
                if (body != null && body.containsKey("data")) {
                    Map<String, Object> data = (Map<String, Object>) ((List<?>) body.get("data")).get(0);
                    return (String) data.get("url");
                }
            }
            return "DALL-E 응답 없음";
        } catch (Exception e) {
            throw new RuntimeException("DALL-E 호출 중 오류 발생: " + e.getMessage(), e);
        }
    }

}
