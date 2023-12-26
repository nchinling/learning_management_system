package com.lms.project.backend.models;

public class QuizQuestions {
    // private String quizQuestionId;
    private String question;
    private String questionType;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private String marks;
    private boolean correct;
    private int numberOfTimesCorrect;
    
    public QuizQuestions() {
    }

    
    public QuizQuestions(String question, String questionType, String option1, String option2,
            String option3, String option4, String answer, String marks) {
        this.question = question;
        this.questionType = questionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.marks = marks;
    }

    
    public QuizQuestions(String question, String questionType, String option1, String option2,
            String option3, String option4, String answer, String marks, boolean correct,
            int numberOfTimesCorrect) {
        this.question = question;
        this.questionType = questionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.marks = marks;
        this.correct = correct;
        this.numberOfTimesCorrect = numberOfTimesCorrect;
    }


    public QuizQuestions(String question, String questionType, String option1, String option2,
            String option3, String option4, String answer, String marks, boolean correct) {
        this.question = question;
        this.questionType = questionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.marks = marks;
        this.correct = correct;
    }


    public QuizQuestions(String question, String questionType, String option1, String option2, String option3,
            String option4, String answer) {
        this.question = question;
        this.questionType = questionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }

    

    public QuizQuestions(String question, String questionType, String answer) {
        this.question = question;
        this.questionType = questionType;
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

    

    public String getOption1() {
        return option1;
    }


    public void setOption1(String option1) {
        this.option1 = option1;
    }


    public String getOption2() {
        return option2;
    }


    public void setOption2(String option2) {
        this.option2 = option2;
    }


    public String getOption3() {
        return option3;
    }


    public void setOption3(String option3) {
        this.option3 = option3;
    }


    public String getOption4() {
        return option4;
    }


    public void setOption4(String option4) {
        this.option4 = option4;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public String getMarks() {
        return marks;
    }


    public void setMarks(String marks) {
        this.marks = marks;
    }


    public boolean isCorrect() {
        return correct;
    }


    public void setCorrect(boolean correct) {
        this.correct = correct;
    }


    public int getNumberOfTimesCorrect() {
        return numberOfTimesCorrect;
    }


    public void setNumberOfTimesCorrect(int numberOfTimesCorrect) {
        this.numberOfTimesCorrect = numberOfTimesCorrect;
    }


    @Override
    public String toString() {
        return "QuizQuestions [question=" + question + ", questionType=" + questionType
                + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3
                + ", option4=" + option4 + ", answer=" + answer + ", marks=" + marks + ", correct="
                + correct + ", numberOfTimesCorrect=" + numberOfTimesCorrect + "]";
    }

    







    
    

    

    





    

    
}



