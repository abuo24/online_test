package com.example.online_test.controller;

import com.example.online_test.model.ResultSucces;
import com.example.online_test.model.Result;
import com.example.online_test.payload.*;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/admin")
//@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;
    @Autowired
    SubjectsService subjectsService;
    @Autowired
    QuestionService questionService;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/user/all")
    public HttpEntity<?> getUSerList(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PostMapping("/user/add")
    public HttpEntity<?> addUser(@RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.create(reqUser));
    }
    @PutMapping("/user/{id}")
    public HttpEntity<?> editUser(@PathVariable String id, @RequestBody ReqUser reqUser){
        return ResponseEntity.ok(userService.editUser(reqUser, id));
    }
    @DeleteMapping("/user/{id}")
    public HttpEntity<?> deleteUser(@PathVariable String id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PostMapping("/subject/add")
    public  ResponseEntity createSubject(@RequestBody SubjectRequest subjectRequest){
        return ResponseEntity.ok(new ResultSucces(true,subjectsService.create(subjectRequest)));
    }
    @PutMapping("/subject/{id}")
    public  ResponseEntity editSubject(@PathVariable String id,@RequestBody SubjectRequest subjectRequest) {
        return ResponseEntity.ok(new ResultSucces(true, subjectsService.edit(id, subjectRequest)));
    }
    @DeleteMapping("/subject/{id}")
    public  ResponseEntity deleteSubject(@PathVariable String id){
        return subjectsService.delete(id)?ResponseEntity.ok(new Result(true,"deleted")):new ResponseEntity(new Result(true,"not deleted"), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/question/add")
    public  ResponseEntity addQuestion(@RequestBody QuestionRequest questionRequest){
        return ResponseEntity.ok(new ResultSucces(true,questionService.create(questionRequest)));
    }
    @PutMapping("/question/{questionId}")
    public  ResponseEntity editQuestionSubjectIdAndTitle(@PathVariable String questionId,@RequestParam String subjectId, @RequestParam String questionUz,@RequestParam String questionRu){
        return ResponseEntity.ok(new ResultSucces(true,questionService.editSubjectsAndTitle(questionId,subjectId,questionRu,questionUz)));
    }
    @PutMapping("/questions/{questionId}")
    public  ResponseEntity editQuestionSubjectIdWithAnswersList(@PathVariable String questionId,@RequestBody QuestionRequest question){
        return ResponseEntity.ok(new ResultSucces(true,questionService.editByAnswersList(questionId,question)));
    }
    @DeleteMapping("/question/{id}")
    public  ResponseEntity deleteQuestion(@PathVariable String id){
        return questionService.delete(id)?ResponseEntity.ok(new Result(true,"deleted")):new ResponseEntity(new Result(true,"not deleted"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/question/{subjectId}")
    public  ResponseEntity getAllQuestionsBySubjectId(@PathVariable String subjectId){
        return ResponseEntity.ok(new ResultSucces(true,questionService.getAllQuestionsListBySubjectId(subjectId))); }
    @GetMapping("/question/all")
    public  ResponseEntity getAllQuestions(){
        return ResponseEntity.ok(new ResultSucces(true,questionService.getAllQuestionsListByCreateDesc()));
    }
    @DeleteMapping("/answer/{id}")
    public  ResponseEntity deleteAnswer(@PathVariable String id){
        return answerService.deleteById(id)?ResponseEntity.ok(new Result(true,"deleted")):new ResponseEntity(new Result(true,"not deleted"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/answer/{answerId}")
    public  ResponseEntity editQuestionSubjectIdAndTitle(@PathVariable String answerId, @RequestParam String title){
        return ResponseEntity.ok(new ResultSucces(true,answerService.edit(answerId,title)));
    }

    @GetMapping("/files")
    public ResponseEntity getShortNewsRelease(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, attachmentService.getPages(page, size)));
    }
    @DeleteMapping("/file/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId){
        attachmentService.delete(hashId);
        return ResponseEntity.ok("file yo'q qilindi");
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile multipartFile){
        attachmentService.save(multipartFile);
        return ResponseEntity.ok(multipartFile.getOriginalFilename()+" file saqlandi");
    }


    @PostMapping("/route/add")
    public ResponseEntity createRoute(@RequestBody RouteRequest routeRequest){
        return ResponseEntity.ok(new ResultSucces(true,routeService.create(routeRequest)));
    }
    @PutMapping("/route/{routeId}")
    public ResponseEntity editRoute(@PathVariable String routeId,@RequestBody RouteRequest routeRequest){
        return ResponseEntity.ok(new ResultSucces(true,routeService.edit(routeId, routeRequest)));
    }
    @DeleteMapping("/route/{routeId}")
    public ResponseEntity delRoute(@PathVariable String routeId){
        return routeService.delete(routeId)?ResponseEntity.ok(new ResultSucces(true,"deleted")): new ResponseEntity(new Result(false, "not deleted"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/course/add")
    public HttpEntity<?> addCourse(@RequestBody ReqCourse reqCourse){
        return courseService.addCourse(reqCourse);
    }
    @PutMapping("/course/{id}")
    public HttpEntity<?> editCourse(@RequestBody ReqCourse reqCourse, @PathVariable String id){
        return courseService.editCourse(reqCourse,id);
    }
    @DeleteMapping("/course/{id}")
    public HttpEntity<?> deleteCourse(@PathVariable String id){
        return courseService.deleteCourse(id);
    }

    @PostMapping("/group/add")
    public HttpEntity<?> addGroups(@RequestBody ReqGroup reqGroup){
        return groupService.addGroups(reqGroup);
    }
    @PutMapping("/group/{id}")
    public HttpEntity<?> editGroups(@RequestBody ReqGroup reqGroup, @PathVariable String id){
        return groupService.editGroup(reqGroup,id);
    }
    @DeleteMapping("/group/{id}")
    public HttpEntity<?> deleteGroups(@PathVariable String id){
        return groupService.deleteGroup(id);
    }

    @GetMapping("/group/all")
    public HttpEntity<?> getGroupList(){
        return groupService.getAllGroups();
    }
    @GetMapping("/group/{id}")
    public HttpEntity<?> getGroupsById(@PathVariable String id){
        return groupService.getOneGroupsById(id);
    }


}
