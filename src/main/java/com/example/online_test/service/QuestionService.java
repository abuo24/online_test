package com.example.online_test.service;

import com.example.online_test.entity.Question;
import com.example.online_test.payload.QuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    public Question create(QuestionRequest question);
    public List<Question> getAllQuestionsListBySubjectId(String subjectId);
    public boolean delete(String questionId);
    public Page<Question> getRandomQuestionListBySubjectId(String subjectId);


}
