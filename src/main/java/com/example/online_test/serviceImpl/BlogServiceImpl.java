package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Blog;
import com.example.online_test.entity.Course;
import com.example.online_test.payload.BlogRequest;
import com.example.online_test.payload.BlokRequest;
import com.example.online_test.payload.ReqCourse;
import com.example.online_test.repository.AttachmentRepository;
import com.example.online_test.repository.BlogRepository;
import com.example.online_test.repository.CourseRepository;
import com.example.online_test.service.AttachmentService;
import com.example.online_test.service.BlogService;
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
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentService attachmentService;

    @Override
    public Map getAllBlogsList(int page, int size) {
        try {
            List<Blog> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Blog> pageTuts = blogRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("blogs", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Blog getBlogById(String id) {

        try {
            Optional<Blog> byId = blogRepository.findById(id);
            if (!byId.isPresent()) {
                return null;
            }
            return blogRepository.getOne(id);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean createBlog(BlogRequest reqCourse) {

        try {
            Blog blog = new Blog();
            blog.setAttachment(attachmentRepository.findByHashId(reqCourse.getHashId()));
            blog.setContentRu(reqCourse.getContentRu());
            blog.setContentUz(reqCourse.getContentUz());
            blog.setTitleRu(reqCourse.getTitleRu());
            blog.setTitleUz(reqCourse.getTitleUz());
            return blogRepository.save(blog) != null;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean editBlog(BlogRequest reqCourse, String id) {
        try {
            Blog blog = blogRepository.getOne(id);
            if (blog == null) {
                return false;
            }
            blog.setAttachment(attachmentRepository.findByHashId(reqCourse.getHashId()));
            blog.setContentRu(reqCourse.getContentRu());
            blog.setContentUz(reqCourse.getContentUz());
            blog.setTitleRu(reqCourse.getTitleRu());
            blog.setTitleUz(reqCourse.getTitleUz());
            return blogRepository.save(blog) != null;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            blogRepository.deleteById(id);
            return true;
        } catch (Exception e) {
        }
        return false;
    }
}
