package com.bree.springproject.onlinebursaryapplication.controller;

import com.bree.springproject.onlinebursaryapplication.Entity.ChiefDataEntity;
import com.bree.springproject.onlinebursaryapplication.service.HandleChiefLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v0/chief")
@Slf4j
public class ChiefsRequestController {

    @Autowired
    HandleChiefLogicService handleChiefLogicService;
    /*Get mapping to get form paths for all students who have committed
    * their forms to the chief for consenting.*/
    @GetMapping("/get-forms/{chiefId}")
    public ResponseEntity<List<ChiefDataEntity>> getSentForm(
            @PathVariable Long chiefId
    )
    {
        log.info("Received a request to get for for a chief.");

        return handleChiefLogicService.getForms(chiefId);
    }


}
