package com.lms.project.backend.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.project.backend.models.StudentAccount;

public class StudentAccountRowMapper implements RowMapper<StudentAccount> {
    @Override
    public StudentAccount mapRow(ResultSet rs, int rowNum) throws SQLException {

        StudentAccount account = new StudentAccount();

        account.setAccountId(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setStudentClass(rs.getString("class"));
        account.setPassword(rs.getString("password"));
        account.setEmail(rs.getString("email"));

        return account;

    }
}






