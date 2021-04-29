package com.example.online_test.service;

import com.example.online_test.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface AttachmentService {
    public void delete(String hashId);
    public Attachment findByHashId(String hashId);
    public String save(MultipartFile multipartFile);
    public Map getPages(int page, int size);
}
