package com.lms.project.backend.models;

public class QuizQuestions {
    // private String quizQuestionId;
    private String question;
    private String questionType;
    private String[] options;
    private String answer;
    
    public QuizQuestions() {
    }

    
    public QuizQuestions(String question, String questionType, String[] options, String answer) {
        this.question = question;
        this.questionType = questionType;
        this.options = options;
        this.answer = answer;
    }



    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    @Override
    public String toString() {
        return "QuizQuestions [question=" + question + ", questionType=" + questionType + ", options=" + options
                + ", answer=" + answer + "]";
    }

    
}



