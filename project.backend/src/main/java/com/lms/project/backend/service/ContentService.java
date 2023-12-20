package com.lms.project.backend.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.lms.project.backend.models.Content;
import com.lms.project.backend.repositories.ContentRepository;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepo;

    public Content saveContent(Content content) throws ContentException {
        System.out.println("The contentId in saveContent is: " + content.getContentId());
        try {

            return contentRepo.saveContent(content);

        } catch (DataIntegrityViolationException ex) {
            String errorMessage = "An error occurred while saving. Please try again.";
            throw new ContentException(errorMessage);
        }
    }

    public List<Content> getAllContent(String accountId) throws IOException {
        System.out.println(">>>>>>>> I am in getAllContentServices>>>>>>");


        List<Content> allContent = contentRepo.getAllContent(accountId);

        List<Content> contentsList = new ArrayList<Content>();
        if (!allContent.isEmpty()) {
            for (Content content : allContent) {

                // Quiz eachQuiz = new Quiz(quiz.getTitle(), quiz.getQuizId(), quiz.getDateCreated(),
                //         quiz.getDateEdited());
                
                Content eachContent = new Content(content.getTitle(), content.getContentId(), content.getDateCreated(), content.getDateEdited(), content.getNumberOfAccess());


                contentsList.add(eachContent);
            }
        } else {
            System.out.println("No quiz found.");
        }


        return contentsList;

    }

    public List<Content> getAllStudentContent(String studentClass) throws IOException {
        System.out.println(">>>>>>>> I am in getAllContentServices>>>>>>");

        List<Content> allContent = contentRepo.getAllStudentContent(studentClass);

        List<Content> contentList = new ArrayList<Content>();
        if (!allContent.isEmpty()) {
            for (Content content : allContent) {

                Content eachContent = new Content(content.getTitle(), content.getContentId(), content.getDateCreated());


                contentList.add(eachContent);
            }
        } else {
            System.out.println("No content found.");
        }


        return contentList;

    }

    // public List<StudentResult> getStudentResult(String studentAccountId) throws IOException {


    //     List<StudentResult> studentResults = quizRepo.getStudentResults(studentAccountId);

    //     List<StudentResult> results = new ArrayList<StudentResult>();
    //     if (!studentResults.isEmpty()) {
    //         for (StudentResult studentResult : studentResults) {

    //             // Quiz eachQuiz = new Quiz(quiz.getTitle(), quiz.getQuizId());
    //             StudentResult eachResult = new StudentResult(studentResult.getStudentAccountId(),
    //                     studentResult.getQuizId(), studentResult.getStudentTotalMarks(),
    //                     studentResult.getQuizTotalMarks(), studentResult.getPercent(),
    //                     studentResult.getDateAttempted());
    //             results.add(eachResult);
    //         }
    //     } else {
    //         System.out.println("No result found.");
    //     }


    //     return results;

    // }


    public Content getContent(String contentId) throws IOException {
        System.out.println(">>>>>>>> I am in getContentService>>>>>>");


        Content content = contentRepo.getContent(contentId);
        contentRepo.saveAnalytics(content);
        return content;

    }


    public void removeContent(String contentId) throws IOException {

        contentRepo.removeContent(contentId);
    }

}
