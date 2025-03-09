package ai.creator.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import java.util.Map;

@Service
public class AIService {

    @Value("${ai.api.key}")
    private String apiKey;

    private final String baseUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=";

    public String getApiUrl() {
        return baseUrl + apiKey;
    }
    
    public String getAIResponse(String question) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String fullUrl = getApiUrl();
        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + question + "\" }]}]}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(fullUrl, HttpMethod.POST, requestEntity, Map.class);
            Map responseBody = response.getBody();
            
            // Log the full response for debugging
            System.out.println("Full API Response: " + responseBody);

            if (responseBody != null && responseBody.containsKey("candidates")) {
                java.util.List candidates = (java.util.List) responseBody.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<String, Object> firstCandidate = (Map<String, Object>) candidates.get(0);
                    Map<String, Object> content = (Map<String, Object>) firstCandidate.get("content");
                    if (content != null && content.containsKey("parts")) {
                        java.util.List parts = (java.util.List) content.get("parts");
                        if (!parts.isEmpty()) {
                            Map<String, Object> firstPart = (Map<String, Object>) parts.get(0);
                            if (firstPart.get("text") != null) {
                                return firstPart.get("text").toString();
                            } else {
                                return "Error: 'text' field is missing in the first part of candidate's content.";
                            }
                        } else {
                            return "Error: 'parts' array is empty.";
                        }
                    } else {
                        return "Error: 'parts' field is missing in the candidate's content.";
                    }
                }
            }
            return "Error: Invalid API response.";

        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}
