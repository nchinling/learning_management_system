package com.lms.project.backend.repositories;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.project.backend.models.Quiz;

public class QuizRowMapper implements RowMapper<Quiz> {
    
    
    @Override
    public Quiz mapRow(ResultSet rs, int rowNum) throws SQLException {


        Quiz quiz = new Quiz();

        quiz.setQuizId(rs.getString("quiz_id"));
        quiz.setAccountId(rs.getString("account_id"));
        quiz.setDateCreated(rs.getDate("date_created"));
        BigDecimal attempts = rs.getBigDecimal("attempts");
        int attemptsAsInt = attempts.intValue();
        quiz.setNumberOfAttempts(attemptsAsInt);
        quiz.setMarks(rs.getString("student_total_marks"));
        quiz.setTotalMarks(rs.getString("quiz_total_marks"));

        return quiz;

    }




}
