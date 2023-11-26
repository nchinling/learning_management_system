package com.lms.project.backend.models;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Quiz {

    private String accountId;
    private String quizId;
    private String title;
    private String status;
    private QuizQuestions[] quizQuestions;
    private String[] quizClasses;
    private String marks;
    private String totalMarks;
    private Date dateCreated;
    private Date dateEdited;
    private int numberOfAttempts;


    public Quiz() {
    }


    public QuizQuestions[] getQuizQuestions() {
        return quizQuestions;
    }

    
    public Quiz(String accountId, String quizId, String title, String status,
            QuizQuestions[] quizQuestions, String[] quizClasses, String marks, String totalMarks,
            Date dateCreated, Date dateEdited, int numberOfAttempts) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
        this.marks = marks;
        this.totalMarks = totalMarks;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
        this.numberOfAttempts = numberOfAttempts;
    }


    public Quiz(String title, String quizId, int numberOfAttempts, Date dateCreated, Date dateEdited) {
        this.quizId = quizId;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
        this.numberOfAttempts = numberOfAttempts;
    }

    public Quiz(String title, String quizId, Date dateCreated, Date dateEdited) {
        this.quizId = quizId;
        this.title = title;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;

    }


    


    public Quiz(String title,String quizId, Date dateCreated) {
        this.quizId = quizId;
        this.title = title;
        this.dateCreated = dateCreated;
    }


    public Quiz(String accountId, String quizId, String title, String status,
            QuizQuestions[] quizQuestions, String[] quizClasses, String marks, String totalMarks,
            Date dateCreated, Date dateEdited) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
        this.marks = marks;
        this.totalMarks = totalMarks;
        this.dateCreated = dateCreated;
        this.dateEdited = dateEdited;
    }


    public Date getDateCreated() {
        return dateCreated;
    }


    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    public Date getDateEdited() {
        return dateEdited;
    }


    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public String getFormattedDateCreated() {
        return formatDate(dateCreated);
    }

    public String getFormattedDateEdited() {
        return formatDate(dateEdited);
    }

    private String formatDate(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
        return sdf.format(date);
    }


    public Quiz(String accountId, String quizId, String title, String status,
            QuizQuestions[] quizQuestions, String[] quizClasses, String marks, String totalMarks) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
        this.marks = marks;
        this.totalMarks = totalMarks;
    }


    public Quiz(String accountId, String quizId, String title, String status,
            QuizQuestions[] quizQuestions, String[] quizClasses, String marks) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
        this.marks = marks;
    }


    public Quiz(String accountId, String title,  String quizId, QuizQuestions[] quizQuestions,
            String[] quizClasses) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
    }


    public Quiz(String accountId, String quizId, String title, String status,
            QuizQuestions[] quizQuestions, String[] quizClasses) {
        this.accountId = accountId;
        this.quizId = quizId;
        this.title = title;
        this.status = status;
        this.quizQuestions = quizQuestions;
        this.quizClasses = quizClasses;
    }


    public void setQuizQuestions(QuizQuestions[] quizQuestions) {
        this.quizQuestions = quizQuestions;
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


    public String[] getQuizClasses() {
        return quizClasses;
    }


    public void setQuizClasses(String[] quizClasses) {
        this.quizClasses = quizClasses;
    }


    public String getMarks() {
        return marks;
    }


    public void setMarks(String marks) {
        this.marks = marks;
    }


    public String getTotalMarks() {
        return totalMarks;
    }


    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }


    public int getNumberOfAttempts() {
        return numberOfAttempts;
    }


    public void setNumberOfAttempts(int numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }


    @Override
    public String toString() {
        return "Quiz [accountId=" + accountId + ", quizId=" + quizId + ", title=" + title
                + ", status=" + status + ", quizQuestions=" + Arrays.toString(quizQuestions)
                + ", quizClasses=" + Arrays.toString(quizClasses) + ", marks=" + marks
                + ", totalMarks=" + totalMarks + ", dateCreated=" + dateCreated + ", dateEdited="
                + dateEdited + ", numberOfAttempts=" + numberOfAttempts + "]";
    }

    


    
}

