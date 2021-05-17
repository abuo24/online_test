package com.example.online_test.controller;

import com.example.online_test.entity.Attachment;
import com.example.online_test.entity.Teacher;
import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.model.ResultSucces;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.security.JwtTokenProvider;
import com.example.online_test.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api/client")

@CrossOrigin(origins = "*", maxAge = 3600)
public class ClientController {
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
    BlogService blogService;
    @Autowired
    RouteService routeService;
    @Value("${upload.folder}")
    private String uploadFolder;

    @GetMapping("/subject/all")
    public ResponseEntity getAllSubjectsList(){
        return ResponseEntity.ok(new ResultSucces(true,subjectsService.subjectsList()));
    }
    @GetMapping("/subject/{id}")
    public ResponseEntity getSubjectById(@PathVariable String id){
        return subjectsService.getOneById(id)!=null?ResponseEntity.ok(new ResultSucces(true,subjectsService.getOneById(id))):new ResponseEntity(new Result(false,"not found"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/blog/all")
    public ResponseEntity getAllBlogsByPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,blogService.getAllBlogsList(page,size)));
    }
    @GetMapping("/blog/{id}")
    public ResponseEntity getBlogById(@PathVariable String id){
        return blogService.getBlogById(id)!=null?ResponseEntity.ok(new ResultSucces(true,blogService.getBlogById(id))):new ResponseEntity(new Result(false,"not found"), HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/subjects/{parentId}")
    public ResponseEntity getSubjectListByIds(@PathVariable String parentId, @RequestParam String parentSecondId){
        return subjectsService.getSubjectListByIds(parentId, parentSecondId)!=null?ResponseEntity.ok(new ResultSucces(true,subjectsService.getSubjectListByIds(parentId,parentSecondId))):new ResponseEntity(new Result(false,"not found"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/file/preview/{hashId}")
    public ResponseEntity preview(@PathVariable String hashId) throws IOException {
        Attachment attachment =  attachmentService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\""+ URLEncoder.encode(attachment.getName()))
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        attachment.getUploadPath())));
    }

    @GetMapping("/file/download/{hashId}")
    public ResponseEntity download(@PathVariable String hashId) throws IOException {
        Attachment attachment =  attachmentService.findByHashId(hashId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\""+ URLEncoder.encode(attachment.getName()))
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s",
                        uploadFolder,
                        attachment.getUploadPath())));
    }

    @GetMapping("/route/{id}")
    public ResponseEntity getRouteById(@PathVariable String id){
        return  routeService.getOneById(id)!=null?ResponseEntity.ok(new ResultSucces(true, routeService.getOneById(id))):new ResponseEntity(new Result(false,"not found route"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/routes/{subFirstId}")
    public ResponseEntity getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectId(@PathVariable String subFirstId, @RequestParam(defaultValue = "") String subjectSecondId, @RequestParam(defaultValue = "") String subjectThirdId){
        if (subjectSecondId.equals("")){
            return  routeService.getRoutesByFirstSubjectId(subFirstId)!=null?ResponseEntity.ok(new ResultSucces(true, routeService.getRoutesByFirstSubjectId(subFirstId))):new ResponseEntity(new Result(false,"not found route"),HttpStatus.BAD_REQUEST);
        }
        if (subjectThirdId.equals("")){
            return  routeService.getRoutesByFirstSubjectIdAndSecondSubjectIds(subFirstId, subjectSecondId)!=null?ResponseEntity.ok(new ResultSucces(true, routeService.getRoutesByFirstSubjectIdAndSecondSubjectIds(subFirstId, subjectSecondId))):new ResponseEntity(new Result(false,"not found route"),HttpStatus.BAD_REQUEST);
        }
        return  routeService.getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(subFirstId, subjectSecondId, subjectThirdId)!=null?ResponseEntity.ok(new ResultSucces(true, routeService.getRoutesByFirstSubjectIdAndSecondSubjectIdAndThirdSubjectIds(subFirstId, subjectSecondId, subjectThirdId))):new ResponseEntity(new Result(false,"not found route"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/history/all")
    public ResponseEntity getHistoryByPagealable(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return  ResponseEntity.ok(new ResultSucces(true, historyService.getAllByPages(page, size)));
    }
    @GetMapping("/route/all")
    public ResponseEntity getAllRoutes(){
        return  ResponseEntity.ok(new ResultSucces(true, routeService.getAllRouteList()));
    }


    @GetMapping("/teacher/all")
    public ResponseEntity getAllteacherByPage( @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(new ResultSucces(true,teacherService.getAllTeachers(page,size)));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity getTeacherById(@PathVariable String id){
        Teacher teacher = teacherService.getOne(id);
        return (teacher!=null?ResponseEntity.ok(new ResultSucces(true,teacher)):(new ResponseEntity(new Result(false,"something went wrong"), HttpStatus.BAD_REQUEST)));
    }

}
