package com.lms.project.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lms.project.backend.repositories.ClassRepository;


@Service
public class ClassService {

        @Autowired
        private ClassRepository classRepo;

        public List<String> getClassList(String accountId) {
        System.out.println(">>>>>>>> I am in Service >>> getClassList");
        return classRepo.getClassList(accountId);
    }

}
