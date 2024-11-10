package com.application.chatbot.Service;

import com.application.chatbot.response.APIResponse;

public interface ChatbotService {

    // This will return the API response
    // come from the AI chatbot
    APIResponse getCoinDetails(String prompt) throws Exception;
    String simpleChat(String prompt);
    
}
