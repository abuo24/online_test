package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, String> {
}
