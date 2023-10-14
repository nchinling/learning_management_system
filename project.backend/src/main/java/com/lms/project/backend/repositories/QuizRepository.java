package com.lms.project.backend.repositories;

import org.springframework.stereotype.Repository;

import com.lms.project.backend.models.Quiz;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;



@Repository
public class QuizRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Quiz saveQuiz(Quiz quiz) {
    // Query query = new Query(Criteria.where("accountId").is(quiz.getAccountId()));
    // Document existingAccountId = mongoTemplate.findOne(query, Document.class, "quiz");

    // if (existingAccountId != null) {
    //     existingAccountId.put("title", quiz.getTitle());
    //     existingAccountId.put("quizId", quiz.getQuizId());
    //     existingAccountId.put("quizQuestions", quiz.getQuestions());
    //     mongoTemplate.save(existingAccountId, "quiz");
    //     System.out.println(">>>>> Updating existing quiz >>>>>>");
    //     return quiz;
    // } else {
        Document doc = new Document();
        doc.put("accountId", quiz.getAccountId());
        doc.put("title", quiz.getTitle());
        doc.put("quizId", quiz.getQuizId());
        doc.put("quizQuestions", quiz.getQuestions());
        mongoTemplate.insert(doc, "quiz");
        System.out.println(">>>>> New quiz created >>>>>>");
        return quiz;
    // }
    }
}
