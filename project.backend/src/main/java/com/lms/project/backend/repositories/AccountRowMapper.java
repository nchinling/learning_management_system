package com.lms.project.backend.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.project.backend.models.Account;


public class AccountRowMapper implements RowMapper<Account> {
    
    @Override
    public Account mapRow(ResultSet rs, int rowNum) throws SQLException {

        Account account = new Account();

        account.setAccountId(rs.getString("account_id"));
        account.setName(rs.getString("name"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setEmail(rs.getString("email"));

        return account;

    }
}


