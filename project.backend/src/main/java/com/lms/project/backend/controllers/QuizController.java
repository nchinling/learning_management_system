package com.lms.project.backend.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lms.project.backend.models.Quiz;
import com.lms.project.backend.models.QuizQuestions;
import com.lms.project.backend.service.QuizException;
import com.lms.project.backend.service.QuizService;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

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
        String classes = form.getFirst("classes");
        System.out.println(">>> The classes are: >>>>>" + classes);

        String quizId = form.getFirst("quizId");
        System.out.println(">>> The quizId is >>>>>" + quizId);
        String title = form.getFirst("title");
        System.out.println(">>> The title is >>>>>" + title);
        String questionsJson = form.getFirst("questions");
        System.out.println(">>> The questions for quiz is >>>>>" + questionsJson);
        ObjectMapper questionsObjectMapper = new ObjectMapper();
        ObjectMapper classesObjectMapper = new ObjectMapper();
        QuizQuestions[] questions;
        String[] classList;
        try {
            questions = questionsObjectMapper.readValue(questionsJson, QuizQuestions[].class);
            classList = classesObjectMapper.readValue(classes, String[].class);
            System.out.println("The classes with Jackson are: " + classList);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format received.");
        }


    
        System.out.println(">>> The accountId for quiz is >>>>>" + accountId);
        System.out.println(">>> The title for quiz is >>>>>" + title);
        System.out.println(">>> The questions for quiz is >>>>>" + questions);

        // For creation of new quiz
        Quiz quiz = new Quiz(accountId, title, quizId, questions, classList);
     
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
    

    @GetMapping(path="/getAllQuiz")
    @ResponseBody
    public ResponseEntity<String> getAllQuiz(
    @RequestParam(required=true) String account_id) throws IOException{
        System.out.println("I am in getAllQuiz server");
        System.out.println(">>>>>>>>accountId in controller>>>>>" + account_id);

        String accountId = account_id;
        
        List<Quiz> quizzes = quizSvc.getAllQuiz(accountId);
        System.out.println("Retrieved in controller: "+ quizzes);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

     
        for (Quiz quiz : quizzes) {
            JsonObjectBuilder quizBuilder = Json.createObjectBuilder()
            .add("quiz_id", quiz.getQuizId())
            .add("title", quiz.getTitle());
            arrayBuilder.add(quizBuilder);
        }

        JsonArray respArray = arrayBuilder.build();
        System.out.println(">>>sending back jsonarray quizResponse data.>>>>>Hooray: " + respArray);
        return ResponseEntity.ok(respArray.toString());

}

@GetMapping(path="/getAllStudentQuiz")
@ResponseBody
public ResponseEntity<String> getAllStudentQuiz(
@RequestParam(required=true) String studentClass) throws IOException{
    System.out.println("I am in getAllStudentQuiz server");
    System.out.println(">>>>>>>>studentClass in controller>>>>>" + studentClass);

    
    List<Quiz> quizzes = quizSvc.getAllStudentQuiz(studentClass);
    System.out.println("Retrieved in controller: "+ quizzes);

    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

    
    for (Quiz quiz : quizzes) {
        JsonObjectBuilder quizBuilder = Json.createObjectBuilder()
        .add("quiz_id", quiz.getQuizId())
        .add("title", quiz.getTitle());
        arrayBuilder.add(quizBuilder);
    }

    JsonArray respArray = arrayBuilder.build();
    System.out.println(">>>sending back jsonarray quizResponse data.>>>>>Hooray: " + respArray);
    return ResponseEntity.ok(respArray.toString());

}


    @GetMapping(path="/getQuiz/{quiz_id}")
    @ResponseBody
    public ResponseEntity<String> getQuiz(@PathVariable String quiz_id) throws IOException{
        
        String quizId = quiz_id;

        Quiz quiz = quizSvc.getQuiz(quizId);
        
        System.out.println("Retrieved quiz from controller: "+ quiz);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (QuizQuestions question : quiz.getQuestions()) {
            JsonObjectBuilder questionBuilder = Json.createObjectBuilder()
            .add("question", question.getQuestion())
            .add("questionType", question.getQuestionType())
            .add("option1", question.getOption1() != null ? question.getOption1() : "")
            .add("option2", question.getOption2() != null ? question.getOption2() : "")
            .add("option3", question.getOption3() != null ? question.getOption3() : "")
            .add("option4", question.getOption4() != null ? question.getOption4() : "")
            .add("answer", question.getAnswer());
            arrayBuilder.add(questionBuilder);
        }

        JsonObject resp = Json.createObjectBuilder()
            .add("title", quiz.getTitle())
            .add("account_id", quiz.getAccountId())
            .add("quiz_id", quiz.getQuizId())
            .add("questions", arrayBuilder)
            .build();

        System.out.println(">>>sending back quiz data.>>>>>Hooray: " + resp);
        return ResponseEntity.ok(resp.toString());

}


@GetMapping(path="/removeQuiz/{quiz_id}")
@ResponseBody
public ResponseEntity<String> removeQuiz(@PathVariable String quiz_id) throws IOException{
   
    System.out.println(">>>>>>>>quiz_id in controller>>>>>" + quiz_id);
   
    String quizId = quiz_id;
    try {
        quizSvc.removeQuiz(quizId);
        System.out.println(">>>>>>>>Successfully removed quiz with id >>>>>" + quiz_id);
            JsonObject resp = Json.createObjectBuilder()
            .add("message", "Successfully removed")
            .build();

        return ResponseEntity.ok(resp.toString());
        
    } catch (Exception e) {
        System.out.println(">>>>>>>>Did not remove quiz with id >>>>>" + quiz_id);
        return ResponseEntity.ok(e.getMessage());
    }

}


}

