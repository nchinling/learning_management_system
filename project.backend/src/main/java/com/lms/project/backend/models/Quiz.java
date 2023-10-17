package com.lms.project.backend.models;

import java.util.Arrays;

public class Quiz {

    private String accountId;
    private String quizId;
    private String title;
    private String status;
    private QuizQuestions[] quizQuestions;
    public Quiz() {
    }

    
    public Quiz(String title, String quizId) {
        this.quizId = quizId;
        this.title = title;
    }

    public Quiz(String accountId, String title, QuizQuestions[] quizQuestions) {
        this.accountId = accountId;
        this.title = title;
        this.quizQuestions = quizQuestions;
    }

  
    public Quiz(String accountId, String title, String quizId, QuizQuestions[] quizQuestions) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.quizQuestions = quizQuestions;
    }

    
    public Quiz(String accountId, String quizId, String title, String status, QuizQuestions[] quizQuestions) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public QuizQuestions[] getQuestions() {
        return quizQuestions;
    }
    public void setQuestions(QuizQuestions[] quizQuestions) {
        this.quizQuestions = quizQuestions;
    }
    public String getQuizId() {
        return quizId;
    }
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return "Quiz [accountId=" + accountId + ", quizId=" + quizId + ", title=" + title + ", status=" + status
                + ", quizQuestions=" + Arrays.toString(quizQuestions) + "]";
    }
    
    
}

