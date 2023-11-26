package com.lms.project.backend.controllers;

import java.io.IOException;
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
import com.lms.project.backend.models.Content;
import com.lms.project.backend.models.ContentNotes;
import com.lms.project.backend.service.ContentException;
import com.lms.project.backend.service.ContentService;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

@Controller
@RequestMapping(path = "/api")
@CrossOrigin(origins = "*")
public class ContentController {

    @Autowired
    private ContentService contentSvc;

    @PostMapping(path = "/saveContent", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveContent(@RequestBody MultiValueMap<String, String> form) {

    // @PostMapping("/saveContent")
    // public ResponseEntity<String> saveContent(
    //     @RequestParam("accountId") String accountId,
    //     @RequestParam("contentId") String contentId,
    //     @RequestParam("title") String title,
    //     @RequestParam("classes") String classes,
    //     @RequestParam("contents") List<Map<String, Object>> contentDataList) {

        System.out.printf(">>> I am inside saveContent>>>>>\n");

        String accountId = form.getFirst("accountId");
        System.out.println(">>> The accountId is >>>>>" + accountId);
        String classes = form.getFirst("classes");
        System.out.println(">>> The classes are: >>>>>" + classes);

        String contentId = form.getFirst("contentId");
        System.out.println(">>> The contentId is >>>>>" + contentId);
        String title = form.getFirst("title");
        System.out.println(">>> The title is >>>>>" + title);
        String contentsJson = form.getFirst("content");
        System.out.println(">>> The contents are is >>>>>" + contentsJson);
        ObjectMapper contentsObjectMapper = new ObjectMapper();
        ObjectMapper classesObjectMapper = new ObjectMapper();
        ContentNotes[] contentNotes;
        String[] classList;
        try {
            contentNotes = contentsObjectMapper.readValue(contentsJson, ContentNotes[].class);
            // contentNotes = contentsObjectMapper.readValue(classes, ContentNotes[].class);
    
            classList = classesObjectMapper.readValue(classes, String[].class);
            System.out.println("The classes with Jackson are: " + classList);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format received.");
        }

        System.out.println(">>> The accountId for content is >>>>>" + accountId);
        System.out.println(">>> The title for content is >>>>>" + title);
        System.out.println(">>> The questions for content is >>>>>" + contentNotes);

        // For creation of new quiz
        // Quiz quiz = new Quiz(accountId, title, quizId, questions, classList);
        Content content = new Content(accountId, contentId, title, contentNotes, classList);

        JsonObject resp = null;

        // Quiz savedQuiz;
        Content savedContent;
        try {
            savedContent = contentSvc.saveContent(content);
            resp = Json.createObjectBuilder().add("accountId", savedContent.getAccountId())
                    .add("quizId", savedContent.getContentId())
                    .add("title", savedContent.getTitle()).add("status", "success").build();

            System.out.printf(">>>Sending back to lms client>>>>>\n");
            return ResponseEntity.ok(resp.toString());

        } catch (ContentException e) {
            String errorMessage = e.getMessage();
            System.out.printf(">>>Quiz Exception occured>>>>>\n");
            resp = Json.createObjectBuilder().add("error", errorMessage).build();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp.toString());
        }
    }

    @GetMapping(path = "/getAllContent")
    @ResponseBody
    public ResponseEntity<String> getAllContent(@RequestParam(required = true) String account_id)
            throws IOException {
        System.out.println(">>>>>>>>accountId in controller>>>>>" + account_id);

        String accountId = account_id;

        List<Content> contentsList = contentSvc.getAllContent(accountId);
        System.out.println("Retrieved in controller: " + contentsList);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Content content : contentsList) {
            JsonObjectBuilder quizBuilder = Json.createObjectBuilder()
                    .add("content_id", content.getContentId())
                    .add("title", content.getTitle())
                    .add("date_created", content.getFormattedDateCreated())
                    .add("date_edited", content.getDateEdited() != null ? content.getFormattedDateEdited() : "");
            arrayBuilder.add(quizBuilder);
        }

        JsonArray respArray = arrayBuilder.build();
        System.out.println(">>>sending back jsonarray quizResponse data.>>>>>Hooray: " + respArray);
        return ResponseEntity.ok(respArray.toString());

    }

    @GetMapping(path = "/getAllStudentContent")
    @ResponseBody
    public ResponseEntity<String> getAllStudentQuiz(
            @RequestParam(required = true) String studentClass,
            @RequestParam(required = true) String accountId) throws IOException {
        System.out.println("I am in getAllStudentContent server");
        System.out.println(">>>>>>>>studentClass in controller>>>>>" + studentClass);

        System.out.println("Retrieved the student account Id: " + accountId);

        List<Content> contentList = contentSvc.getAllStudentContent(studentClass);

        System.out.println("Retrieved in controller: " + contentList);

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        for (Content content : contentList) {
            JsonObjectBuilder contentBuilder = Json.createObjectBuilder()
                    .add("content_id", content.getContentId())
                    .add("title", content.getTitle())
                    .add("date_created", content.getFormattedDateCreated());
            arrayBuilder.add(contentBuilder);
        }

        JsonArray respArray = arrayBuilder.build();
        System.out.println(">>>sending back jsonarray contentResponse data.>>>>>Hooray: " + respArray);
        return ResponseEntity.ok(respArray.toString());

    }

    @GetMapping(path = "/getContent/{content_id}")
    @ResponseBody
    public ResponseEntity<String> getContent(@PathVariable String content_id) throws IOException {

        String contentId = content_id;

        Content content = contentSvc.getContent(contentId);

        System.out.println("Retrieved content from controller: " + content);

        JsonArrayBuilder contentArrayBuilder = Json.createArrayBuilder();
        for (ContentNotes contentNotes : content.getContentNotes()) {
            JsonObjectBuilder contentBuilder = Json.createObjectBuilder()
                    .add("sectionTitle", contentNotes.getSectionTitle())
                    .add("notes", contentNotes.getNotes());
            contentArrayBuilder.add(contentBuilder);
        }

        JsonArrayBuilder classArrayBuilder = Json.createArrayBuilder();
        for (String contentClass : content.getClasses()) {
            classArrayBuilder.add(contentClass);
        }

        JsonObject resp = Json.createObjectBuilder().add("title", content.getTitle())
                .add("account_id", content.getAccountId()).add("content_id", content.getContentId())
                .add("contents", contentArrayBuilder)
                .add("date_created", content.getFormattedDateCreated())
                .add("date_edited", content.getDateEdited() != null ? content.getFormattedDateEdited() : "")
                .add("classes", classArrayBuilder).build();
    

        System.out.println(">>>sending back content data.>>>>>Hooray: " + resp);
        return ResponseEntity.ok(resp.toString());

    }

    @GetMapping(path = "/removeContent/{content_id}")
    @ResponseBody
    public ResponseEntity<String> removeQuiz(@PathVariable String content_id) throws IOException {

        System.out.println(">>>>>>>>content_id in remove quiz controller>>>>>" + content_id);

        String contentId = content_id;
        try {
            contentSvc.removeContent(contentId);
            System.out.println(">>>>>>>>Successfully removed content with id >>>>>" + content_id);
            JsonObject resp = Json.createObjectBuilder().add("message", "Successfully removed").build();

            return ResponseEntity.ok(resp.toString());

        } catch (Exception e) {
            System.out.println(">>>>>>>>Did not remove content with id >>>>>" + content_id);
            return ResponseEntity.ok(e.getMessage());
        }

    }

    // @PostMapping(path = "/markQuiz", consumes =
    // MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    // @ResponseBody
    // public ResponseEntity<String> markQuiz(@RequestBody MultiValueMap<String,
    // String> form) {

    // System.out.printf(">>> I am inside saveQuiz>>>>>\n");

    // String accountId = form.getFirst("accountId");
    // System.out.println(">>> The accountId is >>>>>" + accountId);

    // String quizId = form.getFirst("quizId");
    // System.out.println(">>> The quizId is >>>>>" + quizId);
    // String title = form.getFirst("title");
    // System.out.println(">>> The title is >>>>>" + title);
    // String questionsJson = form.getFirst("questions");
    // System.out.println(">>> The questions are is >>>>>" + questionsJson);

    // ObjectMapper questionsObjectMapper = new ObjectMapper();
    // QuizQuestions[] questions;

    // try {
    // questions = questionsObjectMapper.readValue(questionsJson,
    // QuizQuestions[].class);
    // } catch (JsonProcessingException e) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid format
    // received.");
    // }

    // Quiz quiz = new Quiz(accountId, title, quizId, questions);
    // System.out.println("The quiz data in markQuiz is: " + quiz);

    // JsonObject resp = null;

    // Quiz markedQuiz;
    // try {
    // markedQuiz = quizSvc.markQuiz(quiz);
    // resp = Json.createObjectBuilder().add("accountId", markedQuiz.getAccountId())
    // .add("quizId", markedQuiz.getQuizId()).add("title", markedQuiz.getTitle())
    // .add("questions", questionsJson).add("total_marks",
    // markedQuiz.getTotalMarks())
    // .add("marks", markedQuiz.getMarks()).build();

    // System.out.printf(">>>Sending back to lms client>>>>>\n");
    // return ResponseEntity.ok(resp.toString());

    // } catch (QuizException e) {
    // String errorMessage = e.getMessage();
    // System.out.printf(">>>Quiz Exception occured>>>>>\n");
    // resp = Json.createObjectBuilder().add("error", errorMessage).build();

    // return
    // ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp.toString());
    // }

    // }

}
