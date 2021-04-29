package com.example.online_test.controller;

import com.example.online_test.payload.ReqCourse;
import com.example.online_test.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping("/get/courseList")
    public HttpEntity<?> getCourseList(){
        return courseService.getCourseList();
    }
    @GetMapping("/get/course/{id}")
    public HttpEntity<?> getCourseById(@PathVariable String id){
        return courseService.getCourseById(id);
    }
    @PostMapping("/add/course")
    public HttpEntity<?> addCourse(@RequestBody ReqCourse reqCourse){
        return courseService.addCourse(reqCourse);
    }
    @PutMapping("/edit/course/{id}")
    public HttpEntity<?> editCourse(@RequestBody ReqCourse reqCourse, @PathVariable String id){
        return courseService.editCourse(reqCourse,id);
    }
    @DeleteMapping("/delete/course/{id}")
    public HttpEntity<?> deleteCourse(@PathVariable String id){
        return courseService.deleteCourse(id);
    }

}
