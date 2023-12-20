package com.lms.project.backend.repositories;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.lms.project.backend.models.Content;

public class ContentRowMapper implements RowMapper<Content> {


    @Override
    public Content mapRow(ResultSet rs, int rowNum) throws SQLException {

        Content content = new Content();

        content.setAccountId(rs.getString("account_id"));
        content.setContentId(rs.getString("content_id"));
        content.setNumberOfAccess(rs.getInt("accessed"));
        content.setDateCreated(rs.getDate("date_created"));
        BigDecimal accessed = rs.getBigDecimal("accessed");
        int numberOfAccess = accessed.intValue();
        content.setNumberOfAccess(numberOfAccess);

        return content;

    }



}
