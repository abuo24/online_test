package com.example.online_test.repository;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {

    List<Question> findAllBySubjectsId(String subjectId);
    List<Question> findAllByOrderByCreateAtDesc();
    Page<Question> findAllByOrderByCreateAtDesc(Pageable pageable);
    List<Question> findAllBySubjectsId(String subjectId,
                                       Pageable pageable);
    Long countAllBySubjectsId(String subjectId);
}
