package com.lms.project.backend.repositories;

import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lms.project.backend.models.Content;
import com.lms.project.backend.models.Quiz;
import com.mongodb.client.result.UpdateResult;
import static com.lms.project.backend.repositories.DBQueries.*;

@Repository
public class ContentRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Content saveContent(Content content) {
        Query searchQuery = new Query(Criteria.where("contentId").is(content.getContentId()));
        Document existingDoc = mongoTemplate.findOne(searchQuery, Document.class, "content");

        if (existingDoc != null) {
            // Update the existing document
            Update update = new Update().set("title", content.getTitle())
                    .set("contentNotes", content.getContentNotes())
                    .set("classes", content.getClasses()).set("dateEdited", new Date());

            UpdateResult updateResult = mongoTemplate.updateFirst(searchQuery, update, "content");

            if (updateResult.getModifiedCount() > 0) {
                System.out.println(">>>>> Existing content updated >>>>>>");
            } else {
                System.out.println(">>>>> Existing content not updated >>>>>>");
            }
        } else {
            // Create a new document and save in sql

            jdbcTemplate.update(INSERT_CONTENT, content.getContentId());

            Document doc = new Document();
            doc.put("accountId", content.getAccountId());
            doc.put("title", content.getTitle());
            doc.put("contentId", content.getContentId());
            doc.put("contentNotes", content.getContentNotes());
            doc.put("classes", content.getClasses());
            doc.put("dateCreated", new Date());
            mongoTemplate.insert(doc, "content");
            System.out.println(">>>>> New content created >>>>>>");
        }

        return content;
    }

    public List<Content> getAllContent(String accountId) {
        Criteria criteria = Criteria.where("accountId").is(accountId);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "contentId", "dateCreated", "dateEdited");

        List<Content> result = mongoTemplate.find(query, Document.class, "content").stream()
                .map(doc -> new Content(doc.getString("title"), doc.getString("contentId"),
                        doc.getDate("dateCreated"), doc.getDate("dateEdited")))
                .toList();

        List<Content> contentListFromSql = getContentByTeacherAccountId(accountId);
        if (!contentListFromSql.isEmpty()) {
            System.out.println("Obtained content with row mappers");

            // Iterate over each quiz in contentListFromSql
            for (Content content : result) {
                for (Content sqlContent : contentListFromSql) {
                    // Compare the contentId
                    if (content.getContentId().equals(sqlContent.getContentId())) {
                        // Update the attempts if the contentId matches
                        content.setNumberOfAccess(sqlContent.getNumberOfAccess());
                        break;
                    }
                }
            }
        }

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }

    private List<Content> getContentByTeacherAccountId(String accountId) {
        return jdbcTemplate.query(SELECT_CONTENT_BY_TEACHER_ACCOUNT_ID, new ContentRowMapper(),
                new Object[] {accountId});
    }



    public Content getContent(String contentId) {
        Criteria criteria = Criteria.where("contentId").is(contentId);

        Query query = new Query(criteria);
        // .with(Sort.by(Direction.ASC, "title"));z
        query.fields().exclude("_id").include("accountId", "title", "contentId", "contentNotes",
                "classes", "dateCreated", "dateEdited");

        Content content = mongoTemplate.findOne(query, Content.class, "content");

        System.out.println("Data returned from MongoDB: " + content);

        return content;

    }

    public void removeContent(String contentId) {
        Criteria criteria = Criteria.where("contentId").is(contentId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, Content.class, "content");
    }

    public List<Content> getAllStudentContent(String studentClass) {
        Criteria criteria = Criteria.where("classes").is(studentClass);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "contentId", "dateCreated");

        List<Content> result = mongoTemplate.find(query, Document.class, "content").stream()
                .map(doc -> new Content(doc.getString("title"), doc.getString("contentId"),
                        doc.getDate("dateCreated")))
                .toList();

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }


    @Transactional
    public void saveAnalytics(Content content) {

        try {

            // save into SQL database
            jdbcTemplate.update(INCREMENT_ACCESS_BY_CONTENT_ID, content.getContentId());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error saving analytics", e);
        }

    }

}
