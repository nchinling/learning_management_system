package com.lms.project.backend.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.project.backend.models.StudentResult;

public class StudentResultRowMapper implements RowMapper<StudentResult> {
    @Override
    public StudentResult mapRow(ResultSet rs, int rowNum) throws SQLException {

        StudentResult studentResult = new StudentResult();
        
        studentResult.setStudentAccountId(rs.getString("student_account_id"));
        studentResult.setQuizId(rs.getString("quiz_id"));
        studentResult.setQuizTotalMarks(rs.getInt("quiz_total_marks"));
        studentResult.setStudentTotalMarks(rs.getDouble("student_total_marks"));
        studentResult.setPercent(rs.getDouble("percent"));
        studentResult.setDateAttempted(rs.getDate("date_attempted"));


        return studentResult;

    }
    
}


// public class AccountRowMapper implements RowMapper<Account> {
    
//     @Override
//     public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

//         Account account = new Account();

//         account.setAccountId(rs.getString("account_id"));
//         account.setName(rs.getString("name"));
//         account.setUsername(rs.getString("username"));
//         account.setPassword(rs.getString("password"));
//         account.setEmail(rs.getString("email"));

//         return account;

//     }
// }
