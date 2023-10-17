package com.lms.project.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.lms.project.backend.models.Quiz;
import com.lms.project.backend.repositories.QuizRepository;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepo;
  
    public Quiz saveQuiz(Quiz quiz) throws QuizException {
        try {

            String quizId = UUID.randomUUID().toString().substring(0, 8);
            quiz.setQuizId(quizId);

            return quizRepo.saveQuiz(quiz);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = "An error occurred while saving. Please try again.";
            throw new QuizException(errorMessage);
        }
    }

    
    public List<Quiz> getAllQuiz(String accountId) throws IOException {
        System.out.println(">>>>>>>> I am in getAllQuizService>>>>>>");

     
        List<Quiz> allQuiz = quizRepo.getAllQuiz(accountId);

        System.out.println("The allQuiz in service are " + allQuiz);

        
        List<Quiz> quizzes = new ArrayList<Quiz>();
        if (!allQuiz.isEmpty()) {
        for (Quiz quiz : allQuiz) {

            Quiz eachQuiz = new Quiz(quiz.getTitle(), quiz.getQuizId());
            System.out.println("The quiz retrieved is "+ eachQuiz);

            quizzes.add(eachQuiz);
        }
        } else {
            System.out.println("No quiz found.");
        }


        System.out.println("Returning quiz info." + quizzes);
    
        return quizzes;
        
    }
    
}
