package ai.creator.controller;

import org.springframework.web.bind.annotation.*;

import ai.creator.services.AIService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class HtmlAiController {

    private final AIService openAIService;

    public HtmlAiController(AIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/ask")
    public String askQuestion(@RequestParam String question) {
        return openAIService.getAIResponse(question);
    }
}
