package com.lms.project.backend.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lms.project.backend.models.Account;

import static com.lms.project.backend.repositories.DBQueries.*;

@Repository
public class AccountRepository {
     
    @Autowired 
    JdbcTemplate jdbcTemplate;

    //insert into accounts table
    public boolean createAccount(Account account){

        // Create new account
        return jdbcTemplate.update(INSERT_REGISTRATION, account.getAccountId(), account.getName(), 
                                account.getUsername(), account.getEmail(), account.getPassword()) > 0;

    }


    public Optional<Account> getAccountByUsername(String email){
        List<Account> accounts = jdbcTemplate.query(SELECT_ACCOUNT_BY_EMAIL, 
        new AccountRowMapper() , new Object[]{email});
        
        if (!accounts.isEmpty()) {
            return Optional.of(accounts.get(0));
        } else {
            return Optional.empty();
        }
        
    }


}
