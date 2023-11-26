package com.lms.project.backend.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.lms.project.backend.models.Account;
import com.lms.project.backend.models.Quiz;
import com.lms.project.backend.models.StudentResult;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
                    .set("quizClasses", quiz.getQuizClasses()).set("dateEdited", new Date());

            UpdateResult updateResult = mongoTemplate.updateFirst(searchQuery, update, "quiz");

            if (updateResult.getModifiedCount() > 0) {
                System.out.println(">>>>> Existing quiz updated >>>>>>");
            } else {
                System.out.println(">>>>> Existing quiz not updated >>>>>>");
            }
        } else {
            // Create a new document and save in sql
            int quizTotalMarks = 0;
            for (int i = 0; i < quiz.getQuizQuestions().length; i++) {
                quizTotalMarks =
                        quizTotalMarks + Integer.parseInt(quiz.getQuizQuestions()[i].getMarks());
            }

            jdbcTemplate.update(INSERT_QUIZ, quiz.getQuizId(), quiz.getAccountId(), quizTotalMarks);

            Document doc = new Document();
            doc.put("accountId", quiz.getAccountId());
            doc.put("title", quiz.getTitle());
            doc.put("quizId", quiz.getQuizId());
            doc.put("quizQuestions", quiz.getQuestions());
            doc.put("quizClasses", quiz.getQuizClasses());
            doc.put("dateCreated", new Date());
            mongoTemplate.insert(doc, "quiz");
            System.out.println(">>>>> New quiz created >>>>>>");
        }

        return quiz;
    }



    public List<Quiz> getAllQuiz(String accountId) {
        Criteria criteria = Criteria.where("accountId").is(accountId);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "quizId", "dateCreated", "dateEdited");


        List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
                .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId"),
                        doc.getDate("dateCreated"), doc.getDate("dateEdited")))
                .toList();

        List<Quiz> quizListFromSql = getQuizByTeacherAccountId(accountId);
        if (!quizListFromSql.isEmpty()) {
            System.out.println("Obtained quiz with row mappers");
        
            // Iterate over each quiz in quizListFromSql
            for (Quiz resultQuiz : result) {
                for (Quiz sqlQuiz : quizListFromSql) {
                    // Compare the quizId
                    if (resultQuiz.getQuizId().equals(sqlQuiz.getQuizId())) {
                        // Update the attempts if the quizId matches
                        resultQuiz.setNumberOfAttempts(sqlQuiz.getNumberOfAttempts());
                        break; 
                    }
                }
            }
        }

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }

    private List<Quiz> getQuizByTeacherAccountId(String accountId) {
        return jdbcTemplate.query(SELECT_QUIZ_BY_TEACHER_ACCOUNT_ID, new QuizRowMapper(),
                new Object[] {accountId});
    }


    public List<Quiz> getAllStudentQuiz(String studentClass) {
        Criteria criteria = Criteria.where("quizClasses").is(studentClass);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "quizId");


        List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
                .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId"),
                        doc.getDate("dateCreated")))
                .toList();

        System.out.println("Data returned from MongoDB: " + result);

        return result;
    }


    public List<StudentResult> getStudentResults(String studentAccountId) {
        List<StudentResult> studentResults;

        System.out.println("The student account Id is " + studentAccountId);

        studentResults = jdbcTemplate.query(SELECT_STUDENT_RESULTS_BY_STUDENT_ACCOUNT_ID,
                new StudentResultRowMapper(), new Object[] {studentAccountId});

        // if (!studentResults.isEmpty()) {
        // return Optional.of(studentResults.get(0));
        // } else {
        // return Optional.empty();
        // }

        return studentResults;

    }

    public List<Quiz> getAllStudentQuizResult(String studentClass) {
        Criteria criteria = Criteria.where("quizClasses").is(studentClass);

        Query query = new Query(criteria).with(Sort.by(Direction.ASC, "title"));
        query.fields().exclude("_id").include("title", "quizId");


        List<Quiz> result = mongoTemplate.find(query, Document.class, "quiz").stream()
                .map(doc -> new Quiz(doc.getString("title"), doc.getString("quizId"),
                        doc.getDate("dateCreated")))
                .toList();

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
            // int quizTotalMarks = quiz.getQuestions().length;


            // save into sql database
            // jdbcTemplate.update(INCREMENT_ATTEMPTS_BY_QUIZ_ID, quiz.getQuizId());
            // jdbcTemplate.update(INSERT_MARKS_BY_ACCOUNT_ID_AND_QUIZ_ID, quiz.getAccountId(),
            // quiz.getQuizId(), markedQuiz.getTotalMarks(), markedQuiz.getMarks(), percent);
            System.out.println("The total marks in quizRepo is " + markedQuiz.getTotalMarks());
            return markedQuiz;
        } else {

            return null;
        }

    }


    private Quiz compareAndMarkQuiz(Quiz inputQuiz, Quiz savedQuiz) {

        int marks = 0;
        int totalMarks = 0;
        for (int i = 0; i < inputQuiz.getQuizQuestions().length; i++) {
            String inputAnswer = inputQuiz.getQuizQuestions()[i].getAnswer();
            String savedAnswer = savedQuiz.getQuizQuestions()[i].getAnswer();

            totalMarks = totalMarks + Integer.parseInt(savedQuiz.getQuizQuestions()[i].getMarks());

            if (inputAnswer.equals(savedAnswer)) {
                marks = marks + Integer.parseInt(savedQuiz.getQuizQuestions()[i].getMarks());
                inputQuiz.getQuizQuestions()[i].setCorrect(true);
                System.out.println("The marks obtained for question" + i + " is " + marks);
            } else {
                inputQuiz.getQuizQuestions()[i].setCorrect(false);
            }
        }
        inputQuiz.setMarks(String.valueOf(marks));
        inputQuiz.setTotalMarks(String.valueOf(totalMarks));
        return inputQuiz;
    }

    @Transactional
    public void saveAnalytics(Quiz quiz, Quiz markedQuiz) {

        try {
            double percent = (Double.parseDouble(markedQuiz.getMarks())
                    / Double.parseDouble(markedQuiz.getTotalMarks())) * 100;

            // save into SQL database
            jdbcTemplate.update(INCREMENT_ATTEMPTS_AND_ADD_MARKS_BY_QUIZ_ID, markedQuiz.getMarks(),
                    quiz.getQuizId());
            jdbcTemplate.update(INSERT_MARKS_BY_ACCOUNT_ID_AND_QUIZ_ID, quiz.getAccountId(),
                    quiz.getQuizId(), markedQuiz.getTotalMarks(), markedQuiz.getMarks(), percent);
            System.out.println("The total marks in quizRepo is " + markedQuiz.getTotalMarks());
        } catch (Exception e) {
            // Handle or log the exception
            e.printStackTrace();
            throw new RuntimeException("Error saving analytics", e);
        }


    }



}
