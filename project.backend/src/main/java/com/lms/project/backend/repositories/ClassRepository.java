package com.lms.project.backend.repositories;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static com.lms.project.backend.repositories.DBQueries.*;

@Repository
public class ClassRepository {

    @Autowired 
    JdbcTemplate jdbcTemplate;

    public List<String> getClassList(String accountId) {
    List<String> classList=jdbcTemplate.queryForList(SELECT_CLASSES_BY_ACCOUNTID,String.class, accountId);
    
    return classList;
    }


    
}
