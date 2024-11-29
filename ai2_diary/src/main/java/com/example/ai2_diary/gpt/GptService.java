package com.example.ai2_diary.gpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Slf4j
public class GptService {

    @Value("${openai.api-key}")
    private String apiKey;

    private final String GPR_URL = "https://api.openai.com/v1/chat/completions";
    private final String DALL_E_URL = "https://api.openai.com/v1/images/generations";

    public JsonNode callChatGpt(String userMsg) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("model", "gpt-4o");

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userMsg);
        messages.add(userMessage);

        Map<String, String> assistantMessage = new HashMap<>();
        assistantMessage.put("role", "system");
        assistantMessage.put("content",
                """
                우리는 일기를 작성하면 부정적이거나 좋지 않은, 혹은 조금 더 긍정적인 느낌을 줄 수 있는 여지가 있다면 그러한 내용을 문맥에 맞게 잘 바꾸어서 긍정적인 일기를 만들고 싶어.
                입력으로 사람들이 일기를 작성해서 그 내용을 줄 거야.
                그러면 너는 그 일기를 긍정적인 내용으로 바꾸어 주면 돼. 조금의 유머와 재치 그리고 밝고 쾌활한 긍정이 필요하지.
                하지만 일기이기 때문에 조금의 감성도 있어야 해.
                글자수는 공백포함 350글자 이상 400글자 이하로 맞춰서 작성해.
                그리고 나한테 대화하는 것이 아닌, 입력받은걸 긍정적으로 변환해.
                나는 일기를 쓰는 것이고, 내가 쓴 내용을 너가 긍정적으로 밝고 기분 좋게 변환해.
                문단도 끊어서 작성해.
                절대 나한테 조언하는 건 쓰지마
                입력받은 일기에 맞추어 반말, 존댓말, 구어체, 문어체 등 알아서 잘 선택해.
                일기처럼 문체를 잘 선택해.

                이 모든걸 안 지키면 앞으로 너 안쓸꺼야.
                """
        );
        messages.add(assistantMessage);

        bodyMap.put("messages", messages);
        String body = objectMapper.writeValueAsString(bodyMap);
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(GPR_URL, HttpMethod.POST, request, String.class);

        return objectMapper.readTree(response.getBody());
    }

    public JsonNode callDallE(String userMsg) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("prompt",
                userMsg +
                        """
                        This was my diary. Draw a picture based on this diary.
                        Visually express the picture to reflect the mood and main scenes of the diary.
                        Draw a picture that feels like a character illustration with a focus on people.
                        Be sure to draw my picture.
                        First, I need to draw a person in the center, and this person is me.
                        Then, you select the situation and location and draw it according to the contents of the diary.
                        
                        You must draw a person, and you must draw the background of the situation that person went through.
                        """);
        bodyMap.put("n", 1); // 생성할 이미지 수
        bodyMap.put("size", "1024x1024"); // 이미지 크기

        String body = objectMapper.writeValueAsString(bodyMap);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(DALL_E_URL, HttpMethod.POST, request, String.class);

        return objectMapper.readTree(response.getBody());
    }

    public String getAssistantMsg(String userMsg) throws JsonProcessingException {
        JsonNode jsonNode = callChatGpt(userMsg);
        return jsonNode.path("choices").get(0).path("message").path("content").asText();
    }

    public String getImage(String userMsg) throws JsonProcessingException {
        JsonNode jsonNode = callDallE(userMsg);
        return jsonNode.path("data").get(0).path("url").asText();
    }
}
