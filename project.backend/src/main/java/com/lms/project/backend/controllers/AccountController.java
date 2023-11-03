package com.lms.project.backend.controllers;

import java.io.IOException;
import java.util.Date;


import javax.security.auth.login.AccountException;
import javax.security.auth.login.AccountNotFoundException;

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

import com.lms.project.backend.models.Account;
import com.lms.project.backend.models.StudentAccount;
import com.lms.project.backend.service.AccountService;

import jakarta.json.Json;
import jakarta.json.JsonObject;


@Controller
@RequestMapping(path="/api")
@CrossOrigin(origins="*")
public class AccountController {
    
    @Autowired
    private AccountService accSvc;


	@PostMapping(path="/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ResponseBody
	public ResponseEntity<String> login(@RequestBody MultiValueMap<String, String> form) throws Exception {

        String email = form.getFirst("email");
        String password = form.getFirst("password");
        System.out.println("The password in controller is " + password);
        

        boolean student = Boolean.parseBoolean(form.getFirst("student"));
        System.out.println("This is a student: " + student);

        System.out.printf(">>> I am inside Controller Login >>>>>\n");
        JsonObject resp = null;

        Account teacherLoggedInAccount;
        StudentAccount studentLoggedInAccount;
        if(student == true){
    
            try {
                studentLoggedInAccount = accSvc.studentLoginAccount(email, password);
                resp = Json.createObjectBuilder()
                .add("account_id", studentLoggedInAccount.getAccountId())
                .add("username", studentLoggedInAccount.getEmail())
                .add("timestamp", (new Date()).toString())
                .build();
            } catch (AccountNotFoundException | IOException e) {
                String errorMessage = e.getMessage();
                System.out.printf(">>>Account Exception occured>>>>>\n");   
                resp = Json.createObjectBuilder()
                .add("error", errorMessage)
                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp.toString());
            }

        }
        else{
            try {
                teacherLoggedInAccount = accSvc.teacherLoginAccount(email, password);
                resp = Json.createObjectBuilder()
                .add("account_id", teacherLoggedInAccount.getAccountId())
                .add("username", teacherLoggedInAccount.getUsername())
                .add("timestamp", (new Date()).toString())
                .build();
            } catch (AccountNotFoundException | IOException e) {
                String errorMessage = e.getMessage();
                System.out.printf(">>>Account Exception occured>>>>>\n");   
                resp = Json.createObjectBuilder()
                .add("error", errorMessage)
                .build();

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp.toString());
            }

        }
         

         
        System.out.printf(">>>Successfully logged in>>>>>\n");   

        return ResponseEntity.ok(resp.toString());

    }


	@PostMapping(path="/register", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
	public ResponseEntity<String> register(@RequestBody MultiValueMap<String, String> form) {

        System.out.printf(">>> I am inside Controller Register>>>>>\n");

        String accountId = form.getFirst("account_id");
        String name = form.getFirst("name");
        String username = form.getFirst("username");
        String password = form.getFirst("password");
        String email = form.getFirst("email");

        System.out.println(">>> The accountId for update is >>>>>" + accountId);
        System.out.println(">>> The username for update is >>>>>" + username);
        System.out.println(">>> The password for update is >>>>>" + password);


        // For creation of new account
        Account account = new Account(accountId,name, username, email, password);
 
        JsonObject resp = null;

        try {
            accSvc.createAccount(account);
            Account loggedInAccount;
            try {
                loggedInAccount = accSvc.teacherLoginAccount(email, password);
                 
                resp = Json.createObjectBuilder()
                .add("account_id", loggedInAccount.getAccountId())
                .add("username", loggedInAccount.getUsername())
                .add("timestamp", (new Date()).toString())
                .build();

                
            } catch (AccountNotFoundException e) {
                String errorMessage = e.getMessage();
                System.out.printf(">>>Account Exception occured>>>>>\n");   
                resp = Json.createObjectBuilder()
                .add("error", errorMessage)
                .build();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(resp.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

         System.out.printf(">>>Successfully registered>>>>>\n");   

        } catch (AccountException e) {
            String errorMessage = e.getMessage();
            System.out.printf(">>>Account Exception occured>>>>>\n");   
            resp = Json.createObjectBuilder()
            .add("error", errorMessage)
            .build();
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(resp.toString());
        }

        if (resp == null) {
            resp = Json.createObjectBuilder()
                .add("error", "An unexpected error occurred")
                .build();
        }

        return ResponseEntity.ok(resp.toString());

    }

}
    
