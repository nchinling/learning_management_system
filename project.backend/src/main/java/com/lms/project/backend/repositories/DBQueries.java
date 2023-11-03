package com.lms.project.backend.repositories;

public class DBQueries {
    public static final String INSERT_REGISTRATION = """
        
    insert into accounts(account_id, name, username, email, password)
                      values (?, ?, ?, ? ,?);

  """;

  
  public static final String SELECT_ACCOUNT_BY_EMAIL ="select * from accounts where email = ?";

   public static final String SELECT_STUDENT_ACCOUNT_BY_EMAIL ="select * from student_account where email = ?";
    
  public static final String CHECK_ACCOUNTID_EXISTS = "SELECT COUNT(*) FROM accounts WHERE account_id = ?";

}
