package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Attachment;
import com.example.online_test.entity.Course;
import com.example.online_test.payload.ReqCourse;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.repository.CourseRepository;
import com.example.online_test.service.AttachmentService;
import com.example.online_test.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


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
        return new ResponseEntity<>(courseRepository.findAll(), HttpStatus.OK);
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
        String attachmentId = attachmentService.save(reqCourse.getMultipartFile());
        course.setAttachment(attachmentRepository.getOne(attachmentId));
        return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
    }

    @Override
    public HttpEntity<?> editCourse(ReqCourse reqCourse, String id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        Course course = byId.get();
        course.setTitleUz(reqCourse.getTitleUz());
        course.setTitleRu(reqCourse.getTitleRu());
        course.setDescriptionUz(reqCourse.getDescriptionUz());
        course.setDescriptionRu(reqCourse.getDescriptionRu());
        course.setId(id);
        course.setDurationTime(reqCourse.getDurationTime());
        attachmentService.delete(course.getAttachment().getHashId());
        String attachmentId = attachmentService.save(reqCourse.getMultipartFile());
        course.setAttachment(attachmentRepository.getOne(attachmentId));
        return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
    }
    @Override
    public HttpEntity<?> deleteCourse(String id) {
        Optional<Course> byId = courseRepository.findById(id);
        if (!byId.isPresent()){
            return new ResponseEntity<>("Course not found", HttpStatus.NOT_FOUND);
        }
        attachmentService.delete(byId.get().getAttachment().getHashId());
        courseRepository.delete(byId.get());
        return new ResponseEntity<>("Successfully deleted course", HttpStatus.NO_CONTENT);
    }
}
