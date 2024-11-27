package com.example.ai2_diary.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callExternalApi(String url) {
        return restTemplate.getForObject(url, String.class);
    }
}


