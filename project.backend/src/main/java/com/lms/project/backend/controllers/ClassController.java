package com.lms.project.backend.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.lms.project.backend.service.ClassService;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;

@Controller
@RequestMapping(path="/api")
@CrossOrigin(origins="*")
public class ClassController {

    @Autowired
    private ClassService classSvc;


    @GetMapping(path="/getClasses", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getClasses(@RequestParam(required=true) String accountId) throws IOException{
     
        System.out.println("I am in Class Controller");

       
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		List<String> classList = classSvc.getClassList(accountId);
		classList.stream()
			.map(each -> Json.createObjectBuilder()
						.add("class", each)
						.build()
			)
			.forEach(json -> arrBuilder.add(json));

		return ResponseEntity.ok(arrBuilder.build().toString());
       
    }
    
}


