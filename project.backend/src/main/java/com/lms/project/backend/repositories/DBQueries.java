package com.lms.project.backend.repositories;

public class DBQueries {
    public static final String INSERT_REGISTRATION = """
        
    insert into accounts(account_id, name, username, email, password)
                      values (?, ?, ?, ? ,?);

  """;

    public static final String INSERT_QUIZ = """
        
    insert into quiz_data(quiz_id) values (?);

  """;

  
    public static final String INSERT_MARKS_BY_ACCOUNT_ID_AND_QUIZ_ID = """
        
    insert into student_result(student_account_id, quiz_id, marks) values (?,?,?);

  """;


  public static final String SELECT_ACCOUNT_BY_EMAIL ="select * from accounts where email = ?";

  public static final String SELECT_STUDENT_ACCOUNT_BY_EMAIL ="select * from student_account where email = ?";
    
  public static final String CHECK_ACCOUNTID_EXISTS = "SELECT COUNT(*) FROM accounts WHERE account_id = ?";

  public static final String SELECT_CLASSES_BY_ACCOUNTID = "SELECT class_name FROM class WHERE account_id = ?";

  public static final String INCREMENT_ATTEMPTS_BY_QUIZ_ID = "UPDATE quiz_data SET attempts = attempts + 1 WHERE quiz_id = ?";

}
