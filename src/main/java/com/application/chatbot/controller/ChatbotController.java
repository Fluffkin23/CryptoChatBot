package com.application.chatbot.controller;

import com.application.chatbot.Service.ChatbotService;
import com.application.chatbot.dto.PromptBody;
import com.application.chatbot.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ai/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping
    public ResponseEntity<APIResponse> getCoinDetails(@RequestBody PromptBody promptBody) throws Exception {

        chatbotService.getCoinDetails(promptBody.getPrompt());



        APIResponse apiResponse = new APIResponse();
        apiResponse.setMessage(promptBody.getPrompt());
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/simple")
    public ResponseEntity<String> simpleChatHandler(@RequestBody PromptBody promptBody) throws Exception {

        String response = chatbotService.simpleChat(promptBody.getPrompt());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
