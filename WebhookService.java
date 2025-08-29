package com.example.webhooksql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class WebhookService {

    private final RestTemplate restTemplate;

    @Autowired
    public WebhookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateWebhook() {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        Map<String, String> requestBody = Map.of(
                "name", "Ananya Kaushal",
                "regNo", "22BCE2321",
                "email", "ananya.kaushal2022@vutstudent.ac.in"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        System.out.println("✅ Full Webhook Response: " + response.getBody());

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null && response.getBody().containsKey("webhookUrl")) {
            return response.getBody().get("webhookUrl").toString();
        } else {
            throw new RuntimeException("Failed to get webhook URL");
        }
    }

    public void sendSolution(String webhookUrl, String sqlSolution) {
        Map<String, String> requestBody = Map.of(
                "solution", sqlSolution
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            System.out.println("✅ Solution sent successfully!");
        } else {
            System.out.println("❌ Failed to send solution. Status code: " + response.getStatusCode());
        }
    }
}