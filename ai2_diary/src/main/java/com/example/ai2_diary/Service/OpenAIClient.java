//package com.example.ai2_diary.Service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class OpenAIClient {
//
//    @Value("${openai.api-key}")
//    private String apiKey;
//
//    @Value("${openai.api-url}")
//    private String apiUrl;
//
//    private final RestTemplate restTemplate;
//
//    public OpenAIClient(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    // GPT API 호출한다.
//    public String getChatGPTResponse(String prompt) {
//        String endpoint = apiUrl + "/chat/completions";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("model", "gpt-3.5-turbo");
//        request.put("messages", new Object[]{
//                Map.of("role", "user", "content", prompt)
//        });
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            Map<String, Object> body = response.getBody();
//            if (body != null && body.containsKey("choices")) {
//                Map<String, Object> choice = (Map<String, Object>) ((List<?>) body.get("choices")).get(0);
//                return (String) choice.get("message").get("content");
//            }
//        }
//        return "Error: Unable to fetch response from ChatGPT";
//    }
//
//    // DALL-E API 호출한다.
//    public String generateImage(String prompt) {
//        String endpoint = apiUrl + "/images/generations";
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setBearerAuth(apiKey);
//
//        Map<String, Object> request = new HashMap<>();
//        request.put("prompt", prompt);
//        request.put("n", 1);
//        request.put("size", "1024x1024");
//
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);
//
//        ResponseEntity<Map> response = restTemplate.exchange(endpoint, HttpMethod.POST, entity, Map.class);
//
//        if (response.getStatusCode() == HttpStatus.OK) {
//            Map<String, Object> body = response.getBody();
//            if (body != null && body.containsKey("data")) {
//                Map<String, Object> data = (Map<String, Object>) ((List<?>) body.get("data")).get(0);
//                return (String) data.get("url");
//            }
//        }
//        return "Error: Unable to fetch response from DALL-E";
//    }
//}
