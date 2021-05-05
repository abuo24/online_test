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
@CrossOrigin(origins = "*", maxAge = 3600)
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
    TeacherService teacherService;
    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private GroupService groupService;

    @GetMapping("/user/all")
    public ResponseEntity getUserList( @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,userService.getAllUsersByPagealable(page,size)));
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
    public  ResponseEntity<Result> createSubject(@RequestBody SubjectRequestCreate subjectRequest){
        if (subjectsService.create(subjectRequest)){
            return ResponseEntity.ok(new Result(true,"saved"));
        }
        return new ResponseEntity(new Result(true,"not saved"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/subject/all")
    public  ResponseEntity getAllSubjectsByPage( @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,subjectsService.subjectsListByPage(page, size)));
    }
    @PutMapping("/subject/{id}")
    public  ResponseEntity editSubject(@PathVariable String id,@RequestBody SubjectRequest subjectRequest) {
        return subjectsService.edit(id, subjectRequest)?ResponseEntity.ok(new Result(true,"saved")):(new ResponseEntity(new Result(false, "not save"), HttpStatus.BAD_REQUEST));
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
    public  ResponseEntity getAllQuestions(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,questionService.getAllQuestionsListByCreateDesc(page,size)));
    }
    @DeleteMapping("/answer/{id}")
    public  ResponseEntity deleteAnswer(@PathVariable String id){
        return answerService.deleteById(id)?ResponseEntity.ok(new Result(true,"deleted")):new ResponseEntity(new Result(true,"not deleted"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/answer/{answerId}")
    public  ResponseEntity editAnswerSubjectIdAndTitle(@PathVariable String answerId, @RequestParam String titleUz, @RequestParam String titleRu){
        return ResponseEntity.ok(new ResultSucces(true,answerService.edit(answerId,titleUz, titleRu)));
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
    public ResponseEntity createRoute(@RequestBody RouteRequest routeRequest) {
        return  routeService.create(routeRequest)?ResponseEntity.ok(new ResultSucces(true,"saved")):(new ResponseEntity(new Result(false, "not save"), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/route/all")
    public ResponseEntity getRoutesByPage(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(new ResultSucces(true, routeService.getAllRouteListByPagealable(page, size)));
    }

    @PutMapping("/route/{routeId}")
    public ResponseEntity editRoute(@PathVariable String routeId, @RequestBody RouteRequest routeRequest) {
        return routeService.edit(routeId, routeRequest)?ResponseEntity.ok(new ResultSucces(true,"saved")):(new ResponseEntity(new Result(false, "not save"), HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/route/{routeId}")
    public ResponseEntity delRoute(@PathVariable String routeId){
        return routeService.delete(routeId)?ResponseEntity.ok(new ResultSucces(true,"deleted")):new ResponseEntity(new Result(false, "not deleted"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/course/add")
    public HttpEntity<?> addCourse(@RequestBody ReqCourse reqCourse){
        return courseService.addCourse(reqCourse);
    }
    @PutMapping("/course/{id}")
    public HttpEntity<?> editCourse(@RequestBody ReqCourse reqCourse, @PathVariable String id){
        return courseService.editCourse(reqCourse,id);
    }

    @GetMapping("/course/all")
    public ResponseEntity getCourseListByPage( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,courseService.getCourseListByPage(page,size)));
    }
    @DeleteMapping("/course/{id}")
    public HttpEntity<?> deleteCourse(@PathVariable String id){
        return courseService.deleteCourse(id);
    }

    @PostMapping("/group/add")
    public ResponseEntity addGroups(@RequestBody ReqGroup reqGroup){
        return groupService.addGroups(reqGroup)?ResponseEntity.ok(new Result(true, "saqlandi")):new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/group/{id}")
    public ResponseEntity editGroups(@RequestBody ReqGroup reqGroup, @PathVariable String id){
        return groupService.editGroup(reqGroup,id)?ResponseEntity.ok(new Result(true, "o'zgartirildi")):new ResponseEntity(new Result(false, "xatolik"), HttpStatus.BAD_REQUEST);

    }
    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroups(@PathVariable String id){
        return groupService.deleteGroup(id)?ResponseEntity.ok(new Result(true, "deleted")):new ResponseEntity(new Result(false, "not deleted"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/group/all")
    public ResponseEntity getGroupList( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,groupService.getAllByPages(page,size)));
    }
    @GetMapping("/users/{groupId}")
    public ResponseEntity getUsersByGroupId(@PathVariable String groupId,@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,groupService.getUserByGroupId(groupId, page,size)));
    }
    @PostMapping("/teacher/add")
    public ResponseEntity createTeacher(@RequestBody TeacherRequest reqGroup){
        return teacherService.create(reqGroup)?ResponseEntity.ok(new Result(true, "saqlandi")):new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/teacher/{id}")
    public ResponseEntity editTeacher(@RequestBody TeacherRequest reqGroup, @PathVariable String id){
        return teacherService.editUser(reqGroup,id)?ResponseEntity.ok(new Result(true, "o'zgartirildi")):new ResponseEntity(new Result(false, "xatolik"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/teacher/{id}")
    public ResponseEntity deleteTeacher(@PathVariable String id){
        return teacherService.deleteUser(id)?ResponseEntity.ok(new Result(true, "deleted")):new ResponseEntity(new Result(false, "not deleted"), HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/blog/add")
    public ResponseEntity createBlog(@RequestBody BlogRequest blogRequest){
        return blogService.createBlog(blogRequest)?ResponseEntity.ok(new Result(true, "saqlandi")):new ResponseEntity(new Result(false, "saqlanmadi"), HttpStatus.BAD_REQUEST);
    }
    @PutMapping("/blog/{id}")
    public ResponseEntity editBlog(@RequestBody BlogRequest blogRequest, @PathVariable String id){
        return blogService.editBlog(blogRequest, id)?ResponseEntity.ok(new Result(true, "o'zgartirildi")):new ResponseEntity(new Result(false, "xatolik"), HttpStatus.BAD_REQUEST);
    }
    @DeleteMapping("/blog/{id}")
    public ResponseEntity deleteBlog(@PathVariable String id){
        return blogService.delete(id)?ResponseEntity.ok(new Result(true, "deleted")):new ResponseEntity(new Result(false, "not deleted"), HttpStatus.BAD_REQUEST);
    }

}
