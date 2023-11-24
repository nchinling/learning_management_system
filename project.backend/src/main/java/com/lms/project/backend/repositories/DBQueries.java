package com.lms.project.backend.repositories;

public class DBQueries {
    public static final String INSERT_REGISTRATION = """
        
    insert into accounts(account_id, name, username, email, password)
                      values (?, ?, ?, ? ,?);

  """;

    public static final String INSERT_QUIZ = """
        
    insert into quiz_data(quiz_id, quiz_total_marks) values (?,?);

  """;

    public static final String INSERT_CONTENT = """
        
    insert into content_data(content_id) values (?);

  """;

  
    public static final String INSERT_MARKS_BY_ACCOUNT_ID_AND_QUIZ_ID = """
        
    insert into student_result(student_account_id, quiz_id, quiz_total_marks, student_total_marks, percent) values (?,?,?,?,?);

  """;


  public static final String SELECT_ACCOUNT_BY_EMAIL ="select * from accounts where email = ?";

  public static final String  SELECT_STUDENT_RESULTS_BY_STUDENT_ACCOUNT_ID ="select * from student_result where student_account_id = ?";


  public static final String SELECT_STUDENT_ACCOUNT_BY_EMAIL ="select * from student_account where email = ?";
    
  public static final String CHECK_ACCOUNTID_EXISTS = "SELECT COUNT(*) FROM accounts WHERE account_id = ?";

  public static final String SELECT_CLASSES_BY_ACCOUNTID = "SELECT class_name FROM class WHERE account_id = ?";

  public static final String INCREMENT_ATTEMPTS_AND_ADD_MARKS_BY_QUIZ_ID = "UPDATE quiz_data SET attempts = attempts + 1, student_total_marks = student_total_marks + ? WHERE quiz_id = ? ";

  public static final String INCREMENT_ACCESS_BY_CONTENT_ID = "UPDATE content_data SET accessed = accessed + 1 WHERE content_id = ? ";

}
