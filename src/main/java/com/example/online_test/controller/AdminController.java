package com.example.online_test.controller;

import com.example.online_test.payload.ReqUser;
import com.example.online_test.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/user/all")
    public HttpEntity<?> getUSerList(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/user/add")
    public HttpEntity<?> addUser(@RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.create(reqUser));
    }
    @PutMapping("/user/edit/{id}")
    public HttpEntity<?> editUser(@PathVariable String id, @RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.editUser(reqUser, id));
    }
    @DeleteMapping("/user/delete/{id}")
    public HttpEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }





}
