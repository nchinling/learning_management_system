package com.lms.project.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println("The quizId in saveQuiz is: " + quiz.getQuizId());
        try {

            return quizRepo.saveQuiz(quiz);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = "An error occurred while saving. Please try again.";
            throw new QuizException(errorMessage);
        }
    }

    
    public List<Quiz> getAllQuiz(String accountId) throws IOException {
        System.out.println(">>>>>>>> I am in getAllQuizServices>>>>>>");

     
        List<Quiz> allQuiz = quizRepo.getAllQuiz(accountId);

        List<Quiz> quizzes = new ArrayList<Quiz>();
        if (!allQuiz.isEmpty()) {
        for (Quiz quiz : allQuiz) {

            Quiz eachQuiz = new Quiz(quiz.getTitle(), quiz.getQuizId());


            quizzes.add(eachQuiz);
        }
        } else {
            System.out.println("No quiz found.");
        }

    
        return quizzes;
        
    }


    public Quiz getQuiz(String quizId) throws IOException {
        System.out.println(">>>>>>>> I am in getQuizService>>>>>>");

        
        Quiz quiz = quizRepo.getQuiz(quizId);
        return quiz;
        
    }


    public void removeQuiz(String quizId) throws IOException {

        quizRepo.removeQuiz(quizId);
    }


}
