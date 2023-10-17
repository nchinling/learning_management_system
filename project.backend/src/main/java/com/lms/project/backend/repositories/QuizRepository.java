package com.lms.project.backend.repositories;

import org.springframework.stereotype.Repository;

import com.lms.project.backend.models.Quiz;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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


    public List<Quiz> getAllQuiz(String accountId){
        Criteria criteria = Criteria.where("accountId").is(accountId);      

		Query query = new Query(criteria)
			.with(Sort.by(Direction.ASC, "title"));
		query.fields()
			.exclude("_id")
			.include("title", "quizId");


            List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
            .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId")))
            .toList();

            System.out.println("Data returned from MongoDB: " + result);

            return result;
    }

}
