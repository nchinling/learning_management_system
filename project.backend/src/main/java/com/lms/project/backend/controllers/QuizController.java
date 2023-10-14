package com.lms.project.backend.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lms.project.backend.models.Quiz;
import com.lms.project.backend.models.QuizQuestions;
import com.lms.project.backend.service.QuizException;
import com.lms.project.backend.service.QuizService;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Controller
@RequestMapping(path="/api")
@CrossOrigin(origins="*")
public class QuizController {

    @Autowired
    private QuizService quizSvc;
    
	@PostMapping(path="/saveQuiz", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
	public ResponseEntity<String> saveQuiz(@RequestBody MultiValueMap<String, String> form) {

        System.out.printf(">>> I am inside saveQuiz>>>>>\n");

        String accountId = form.getFirst("accountId");
        System.out.println(">>> The accountId is >>>>>" + accountId);
        String title = form.getFirst("title");
        System.out.println(">>> The title is >>>>>" + title);
        String questionsJson = form.getFirst("questions");
        System.out.println(">>> The questions for quiz is >>>>>" + questionsJson);
        ObjectMapper objectMapper = new ObjectMapper();
        QuizQuestions[] questions;
        try {
            questions = objectMapper.readValue(questionsJson, QuizQuestions[].class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid 'questions' format.");
        }
        

        System.out.println(">>> The accountId for quiz is >>>>>" + accountId);
        System.out.println(">>> The title for quiz is >>>>>" + title);
        System.out.println(">>> The questions for quiz is >>>>>" + questions);

        // For creation of new quiz
        Quiz quiz = new Quiz(accountId, title, questions);
     
        JsonObject resp = null;

    
            Quiz savedQuiz;
            try {
                savedQuiz = quizSvc.saveQuiz(quiz);
                resp = Json.createObjectBuilder()
                .add("accountId", savedQuiz.getAccountId())
                .add("quizId", savedQuiz.getQuizId())
                .add("title", savedQuiz.getTitle())
                .add("status", "success")
                .build();

                System.out.printf(">>>Sending back to lms client>>>>>\n");   
                return ResponseEntity.ok(resp.toString());

            } catch (QuizException e) {
                           String errorMessage = e.getMessage();
            System.out.printf(">>>Quiz Exception occured>>>>>\n");   
            resp = Json.createObjectBuilder()
            .add("error", errorMessage)
            .build();
        
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(resp.toString());
            }
        }
            


}

