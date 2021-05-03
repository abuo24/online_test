package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Subjects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectsRepository extends JpaRepository<Subjects, String> {
    Page<Subjects> findAll(Pageable pageable);
}
