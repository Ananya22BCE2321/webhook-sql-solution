package com.example.webhooksql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SubmitService {

    private final RestTemplate restTemplate;

    @Autowired
    public SubmitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void submit(String accessToken, String sqlQuery) {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, String> body = Map.of("finalQuery", sqlQuery);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

        System.out.println("📤 Submission Response: " + response.getBody());
    }
}