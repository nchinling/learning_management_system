package com.lms.project.backend.models;

import java.util.Date;

public class StudentResult {

    private String studentAccountId;
    private String quizId;
    private int quizTotalMarks;
    private double studentTotalMarks;
    private double percent;
    private Date dateAttempted;
    public StudentResult() {}
    public StudentResult(String studentAccountId, String quizId, double studentTotalMarks,int quizTotalMarks, 
        double percent, Date dateAttempted) {
        this.studentAccountId = studentAccountId;
        this.quizId = quizId;
        this.quizTotalMarks = quizTotalMarks;
        this.studentTotalMarks = studentTotalMarks;
        this.percent = percent;
        this.dateAttempted = dateAttempted;
    }

    // StudentResult eachResult = new StudentResult(studentResult.getStudentAccountId(),studentResult.getQuizId(), studentResult.getStudentTotalMarks(),
    // studentResult.getQuizTotalMarks(), studentResult.getPercent(), studentResult.getDateAttempted());

    

    public StudentResult(String quizId, double studentTotalMarks, int quizTotalMarks, 
            double percent, Date dateAttempted) {
        this.quizId = quizId;
        this.quizTotalMarks = quizTotalMarks;
        this.studentTotalMarks = studentTotalMarks;
        this.percent = percent;
        this.dateAttempted = dateAttempted;
    }
    // StudentResult eachResult = new StudentResult(studentResult.getQuizId(), studentResult.getStudentTotalMarks()
    // studentResult.getQuizTotalMarks(), studentResult.getPercent(), student.getDateCreated());
    public String getStudentAccountId() {
        return studentAccountId;
    }
    public void setStudentAccountId(String studentAccountId) {
        this.studentAccountId = studentAccountId;
    }
    public String getQuizId() {
        return quizId;
    }
    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }
    public int getQuizTotalMarks() {
        return quizTotalMarks;
    }
    public void setQuizTotalMarks(int quizTotalMarks) {
        this.quizTotalMarks = quizTotalMarks;
    }
    public double getStudentTotalMarks() {
        return studentTotalMarks;
    }
    public void setStudentTotalMarks(double studentTotalMarks) {
        this.studentTotalMarks = studentTotalMarks;
    }
    public double getPercent() {
        return percent;
    }
    public void setPercent(double percent) {
        this.percent = percent;
    }
    public Date getDateAttempted() {
        return dateAttempted;
    }
    public void setDateAttempted(Date dateAttempted) {
        this.dateAttempted = dateAttempted;
    }
    @Override
    public String toString() {
        return "StudentResult [studentAccountId=" + studentAccountId + ", quizId=" + quizId
                + ", quizTotalMarks=" + quizTotalMarks + ", studentTotalMarks=" + studentTotalMarks
                + ", percent=" + percent + ", dateAttempted=" + dateAttempted + "]";
    }

    
    


    
    
}
