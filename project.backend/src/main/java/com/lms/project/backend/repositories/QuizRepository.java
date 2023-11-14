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
import org.springframework.jdbc.core.JdbcTemplate;
import static com.lms.project.backend.repositories.DBQueries.*;



@Repository
public class QuizRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired 
    JdbcTemplate jdbcTemplate;


    public Quiz saveQuiz(Quiz quiz) {
        Query searchQuery = new Query(Criteria.where("quizId").is(quiz.getQuizId()));
        Document existingDoc = mongoTemplate.findOne(searchQuery, Document.class, "quiz");

        if (existingDoc != null) {
            // Update the existing document
            Update update = new Update().set("title", quiz.getTitle())
                    .set("quizQuestions", quiz.getQuestions())
                    .set("quizClasses", quiz.getQuizClasses());

            UpdateResult updateResult = mongoTemplate.updateFirst(searchQuery, update, "quiz");

            if (updateResult.getModifiedCount() > 0) {
                System.out.println(">>>>> Existing quiz updated >>>>>>");
            } else {
                System.out.println(">>>>> Existing quiz not updated >>>>>>");
            }
        } else {
            // Create a new document and save in sql 

            jdbcTemplate.update(INSERT_QUIZ, quiz.getQuizId());

            Document doc = new Document();
            doc.put("accountId", quiz.getAccountId());
            doc.put("title", quiz.getTitle());
            doc.put("quizId", quiz.getQuizId());
            doc.put("quizQuestions", quiz.getQuestions());
            doc.put("quizClasses", quiz.getQuizClasses());
            mongoTemplate.insert(doc, "quiz");
            System.out.println(">>>>> New quiz created >>>>>>");
        }

        return quiz;
    }



    public List<Quiz> getAllQuiz(String accountId) {
        Criteria criteria = Criteria.where("accountId").is(accountId);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "quizId");


        List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
                .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId"))).toList();

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }


    public List<Quiz> getAllStudentQuiz(String studentClass) {
        Criteria criteria = Criteria.where("quizClasses").is(studentClass);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "quizId");


        List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
                .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId"))).toList();

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }


    public Quiz getQuiz(String quizId) {
        Criteria criteria = Criteria.where("quizId").is(quizId);

        Query query = new Query(criteria);
        // .with(Sort.by(Direction.ASC, "title"));z
        query.fields().exclude("_id").include("accountId", "title", "quizId", "quizQuestions",
                "quizClasses");


        Quiz quiz = mongoTemplate.findOne(query, Quiz.class, "quiz");

        System.out.println("Data returned from MongoDB: " + quiz);

        return quiz;

    }

    public void removeQuiz(String quizId) {
        Criteria criteria = Criteria.where("quizId").is(quizId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, Quiz.class, "quiz");
    }


    public Quiz markQuiz(Quiz quiz) {
        Criteria criteria = Criteria.where("quizId").is(quiz.getQuizId());
        Query query = new Query(criteria);

        Quiz savedQuiz = mongoTemplate.findOne(query, Quiz.class);
        if (savedQuiz != null) {
            Quiz markedQuiz = compareAndMarkQuiz(quiz, savedQuiz);
            System.out.println("The marks obtained is: " + markedQuiz.getMarks());

            // save into sql database
            jdbcTemplate.update(INCREMENT_ATTEMPTS_BY_QUIZ_ID, quiz.getQuizId());
            jdbcTemplate.update(INSERT_MARKS_BY_ACCOUNT_ID_AND_QUIZ_ID, quiz.getAccountId(), quiz.getQuizId(), quiz.getMarks());

            return markedQuiz;
        } else {

            return null;
        }

    }


    private Quiz compareAndMarkQuiz(Quiz inputQuiz, Quiz savedQuiz) {

        int marks = 0;
        for (int i = 0; i < inputQuiz.getQuizQuestions().length; i++) {
            String inputAnswer = inputQuiz.getQuizQuestions()[i].getAnswer();
            String savedAnswer = savedQuiz.getQuizQuestions()[i].getAnswer();

            if (inputAnswer.equals(savedAnswer)) {
                marks++;
            }

        }
        inputQuiz.setMarks(String.valueOf(marks));
        return inputQuiz;
    }


}
