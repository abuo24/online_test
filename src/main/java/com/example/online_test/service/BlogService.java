package com.example.online_test.service;

import com.example.online_test.entity.Blog;
import com.example.online_test.payload.BlogRequest;

import java.util.Map;

public interface BlogService {
    public boolean delete(String id);
    public boolean editBlog(BlogRequest reqCourse, String id);
    public boolean createBlog(BlogRequest reqCourse);
    public Blog getBlogById(String id);
    public Map getAllBlogsList(int page, int size);
}
