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
import com.lms.project.backend.models.Content;
import com.lms.project.backend.models.Quiz;
import com.mongodb.client.result.UpdateResult;

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
                    .set("classes", content.getClasses())
                    .set("dateEdited", new Date());

            UpdateResult updateResult = mongoTemplate.updateFirst(searchQuery, update, "content");

            if (updateResult.getModifiedCount() > 0) {
                System.out.println(">>>>> Existing content updated >>>>>>");
            } else {
                System.out.println(">>>>> Existing content not updated >>>>>>");
            }
        } else {
            // Create a new document and save in sql

            // jdbcTemplate.update(INSERT_CONTENT, content.getContentId(), contentTotalMarks);

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

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }

    public Content getContent(String contentId) {
        Criteria criteria = Criteria.where("contentId").is(contentId);

        Query query = new Query(criteria);
        // .with(Sort.by(Direction.ASC, "title"));z
        query.fields().exclude("_id").include("accountId", "title", "contentId", "contentNotes",
                "classes");


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


}
