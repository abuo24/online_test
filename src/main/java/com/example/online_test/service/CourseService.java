package com.example.online_test.service;


import com.example.online_test.payload.ReqCourse;
import com.example.online_test.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface CourseService {

    public HttpEntity<?> getCourseList();

    public HttpEntity<?> getCourseById(String id);

    public HttpEntity<?> addCourse(ReqCourse reqCourse);

    public HttpEntity<?> editCourse(ReqCourse reqCourse, String id);

    public HttpEntity<?> deleteCourse(String id);

    public Map getCourseListByPage(int page, int size);


}
