package com.application.chatbot.controller;

import com.application.chatbot.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ResponseEntity<APIResponse>Home(){
        APIResponse response = new APIResponse();
        response.setMessage("Welcome to AI Chatbot");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
