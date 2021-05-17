package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Attachment;
import com.example.online_test.entity.Course;
import com.example.online_test.entity.Groups;
import com.example.online_test.payload.ReqCourse;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.repository.CourseRepository;
import com.example.online_test.service.AttachmentService;
import com.example.online_test.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentService attachmentService;

    @Override
    public HttpEntity<?> getCourseList() {
        try {
            return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){

        }
        return null;
    }
    @Override
    public Map getCourseListByPage(int page, int size) {
        try {
            List<Course> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Course> pageTuts = courseRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("courses", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        }catch (Exception e){

        }
        return null;
    }

    @Override
    public HttpEntity<?> getCourseById(String id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(byId.get(), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> addCourse(ReqCourse reqCourse) {
        Course course=new Course();
        course.setTitleUz(reqCourse.getTitleUz());
        course.setTitleRu(reqCourse.getTitleRu());
        course.setDurationTime(reqCourse.getDurationTime());
        course.setDescriptionUz(reqCourse.getDescriptionUz());
        course.setDescriptionRu(reqCourse.getDescriptionRu());
        course.setAttachment(attachmentRepository.findByHashId(reqCourse.getHashId()));
        return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
    }

    @Override
    public Course editCourse(ReqCourse reqCourse, String id) {

        try {
            Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent()){
            return null;
        }
        Course course = byId.get();
        course.setTitleUz(reqCourse.getTitleUz());
        course.setTitleRu(reqCourse.getTitleRu());
        course.setDescriptionUz(reqCourse.getDescriptionUz());
        course.setDescriptionRu(reqCourse.getDescriptionRu());
        course.setId(id);
        course.setDurationTime(reqCourse.getDurationTime());
        if (reqCourse.getHashId().trim()!=""||reqCourse.getHashId() != null){
            attachmentService.delete(attachmentRepository.findById(byId.get().getAttachment().getHashId()).get().getHashId());
            course.setAttachment(attachmentRepository.findByHashId(reqCourse.getHashId()));
        } else {
            attachmentService.delete(attachmentRepository.findById(byId.get().getAttachment().getHashId()).get().getHashId());
        }
        return courseRepository.save(course);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public boolean deleteCourse(String id) {
        try {

            Optional<Course> byId = courseRepository.findById(id);
            if (!byId.isPresent()) {
                return false;
            }
            if (byId.get().getAttachment() != null) {
                attachmentService.delete(attachmentRepository.findById(byId.get().getAttachment().getHashId()).get().getHashId());
            }
            courseRepository.delete(byId.get());
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
