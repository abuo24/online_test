package com.example.online_test.controller;


import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.model.ResultSucces;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.security.JwtTokenProvider;
import com.example.online_test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")

@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private HistoryService historyService;
    @Autowired
    SubjectsService subjectsService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    BlokService blokService;
    @Autowired
    RouteService routeService;

    @GetMapping("/verifying/{blokId}")
    public ResponseEntity finishedTest(@PathVariable String blokId, HttpServletRequest request){
        User user = userRepository.findByPhoneNumber(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(request))).get();
        if (user==null){
            return ResponseEntity.ok(new Result(false,"token is invalid"));
        }
        return ResponseEntity.ok(blokService.verifyAllTests(user.getId(), blokId));
    }
    @PostMapping("/blok/{questionId}")
    public ResponseEntity saveOneAnswerByQuestionIdAndSelectedAnswerId(@PathVariable String questionId, @RequestParam String selectedId, HttpServletRequest request){
        User user = userRepository.findByPhoneNumber(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(request))).get();
        if (user==null){
            return ResponseEntity.ok(new Result(false,"token is invalid"));
        }
        return blokService.saveAnswerReq(user, questionId, selectedId)?ResponseEntity.ok(new Result(true, "saqlandi")):new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/blok")
    public ResponseEntity createBlokWithSubjectIdsAndUserId(@RequestBody BlokRequest blokRequest, HttpServletRequest httpServletRequest){
        User user = userRepository.findByPhoneNumber(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(httpServletRequest))).get();
        return user!=null?ResponseEntity.ok(new ResultSucces(true, blokService.create(user.getId(),blokRequest))):new ResponseEntity(new Result(false,"not authentication"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/blok/process")
    public ResponseEntity isProcessingBlogByUserId(HttpServletRequest httpServletRequest){
        User user = userRepository.findByPhoneNumber(jwtTokenProvider.getUser(jwtTokenProvider.resolveToken(httpServletRequest))).get();
        return blokService.isProcessingBlokWithUserId(user.getId())!=null?ResponseEntity.ok(new ResultSucces(true, blokService.isProcessingBlokWithUserId(user.getId()))):new ResponseEntity(new Result(false,"not processing user"),HttpStatus.OK);
    }
}
