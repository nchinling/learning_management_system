package com.lms.project.backend.models;

public class StudentAccount {
    private String accountId;
    private String name;
    private String email;
    private String password;
    private String studentClass;
    
    public StudentAccount() {
    }
    public StudentAccount(String accountId, String name, String email, String password, String studentClass) {
        this.accountId = accountId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.studentClass = studentClass;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getStudentClass() {
        return studentClass;
    }
    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }
    @Override
    public String toString() {
        return "StudentAccount [accountId=" + accountId + ", name=" + name + ", email=" + email + ", password="
                + password + ", studentClass=" + studentClass + "]";
    }

    


}
