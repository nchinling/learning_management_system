package com.lms.project.backend.repositories;

import org.springframework.stereotype.Repository;

import com.lms.project.backend.models.Quiz;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;



@Repository
public class QuizRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    // public Quiz saveQuiz(Quiz quiz) {
    //     Document doc = new Document();
    //     doc.put("accountId", quiz.getAccountId());
    //     doc.put("title", quiz.getTitle());
    //     doc.put("quizId", quiz.getQuizId());
    //     doc.put("quizQuestions", quiz.getQuestions());
    //     mongoTemplate.insert(doc, "quiz");
    //     System.out.println(">>>>> New quiz created >>>>>>");
    //     return quiz;

    // }

    public Quiz saveQuiz(Quiz quiz) {
        Query searchQuery = new Query(Criteria.where("quizId").is(quiz.getQuizId()));
        Document existingDoc = mongoTemplate.findOne(searchQuery, Document.class, "quiz");

        if (existingDoc != null) {
            // Update the existing document
            Update update = new Update()
                .set("title", quiz.getTitle())
                .set("quizQuestions", quiz.getQuestions());
            
            UpdateResult updateResult = mongoTemplate.updateFirst(searchQuery, update, "quiz");
            
            if (updateResult.getModifiedCount() > 0) {
                System.out.println(">>>>> Existing quiz updated >>>>>>");
            } else {
                System.out.println(">>>>> Existing quiz not updated >>>>>>");
            }
        } else {
            // Create a new document
            Document doc = new Document();
            doc.put("accountId", quiz.getAccountId());
            doc.put("title", quiz.getTitle());
            doc.put("quizId", quiz.getQuizId());
            doc.put("quizQuestions", quiz.getQuestions());
            mongoTemplate.insert(doc, "quiz");
            System.out.println(">>>>> New quiz created >>>>>>");
        }

        return quiz;
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


    public Quiz getQuiz(String quizId){
        Criteria criteria = Criteria.where("quizId").is(quizId);      

		Query query = new Query(criteria);
			// .with(Sort.by(Direction.ASC, "title"));z
		query.fields()
			.exclude("_id")
			.include("accountId", "title", "quizId", "quizQuestions");


            Quiz quiz = mongoTemplate.findOne(query, Quiz.class, "quiz");

            System.out.println("Data returned from MongoDB: " + quiz);
        
        return quiz;

   }

   public void removeQuiz(String quizId){
    Criteria criteria = Criteria.where("quizId").is(quizId);  
    Query query = new Query(criteria);
    mongoTemplate.remove(query, Quiz.class, "quiz");
   }


}
