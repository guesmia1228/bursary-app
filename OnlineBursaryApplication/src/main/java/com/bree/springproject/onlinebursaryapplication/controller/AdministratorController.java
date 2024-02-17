package com.bree.springproject.onlinebursaryapplication.controller;


import com.bree.springproject.onlinebursaryapplication.models.PrivilegedUserModel;
import com.bree.springproject.onlinebursaryapplication.models.ResponseModel;
import com.bree.springproject.onlinebursaryapplication.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v0/admin/")
@Slf4j
public class AdministratorController {

    @Autowired
    AdminService adminService;

    @PostMapping("/create-higher-users/{adminId}")
    public ResponseEntity<ResponseModel> createPrivilegedUsers(
            @PathVariable Long adminId,
            @RequestBody PrivilegedUserModel privilegedUserModel
            )
    {

        log.warn("Request to create a privileged user");

        return adminService.createUser(adminId, privilegedUserModel);
    }

}
