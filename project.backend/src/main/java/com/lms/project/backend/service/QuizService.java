package com.lms.project.backend.service;

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
}
