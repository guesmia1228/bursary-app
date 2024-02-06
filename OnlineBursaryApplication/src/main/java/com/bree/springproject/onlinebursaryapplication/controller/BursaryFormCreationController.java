package com.bree.springproject.onlinebursaryapplication.controller;

import com.bree.springproject.onlinebursaryapplication.Entity.ApplicationFormCreateTable;
import com.bree.springproject.onlinebursaryapplication.Entity.UserRegistrationTable;
import com.bree.springproject.onlinebursaryapplication.service.CreateFormService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v0/create-form")
@Slf4j
public class BursaryFormCreationController {

    @Autowired
    CreateFormService createFormService;

    @PostMapping("/create-form/{section}")
    public ResponseEntity<String> createForm(@PathVariable String section,
                                             @RequestBody Map<String, String> formSectionA,
                                             @RequestParam String month, @RequestParam Long userId)
    {
        log.info("Receive a request to create a form");
        //here the administrator will fill the field names required for section A.
        return createFormService.createSectionA(formSectionA, month, userId, section);
    }

    @PutMapping("/update-form")
    public ResponseEntity<String> updateForm(@RequestBody
                                             List<ApplicationFormCreateTable> updatedSection)
    {
        log.info("Received a request update the form");

        return createFormService.updateForm(updatedSection);
    }

    @GetMapping("/get-form")
    public ResponseEntity<List<List<ApplicationFormCreateTable>>> getForm()
    {
        //here we will get a list of the list of fields for each section.

        log.info("Received a request to get the form fields");

        return createFormService.getForm();
    }


}