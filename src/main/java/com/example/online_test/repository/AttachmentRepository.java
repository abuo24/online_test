package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    Attachment findByHashId(String hashId);
}
