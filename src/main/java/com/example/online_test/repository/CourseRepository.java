package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    Page<Course> findAllByOrderByCreateAtDesc(Pageable pageable);

}
