package com.example.online_test.service;

import com.example.online_test.entity.Question;
import com.example.online_test.payload.QuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface QuestionService {
    public Question create(QuestionRequest question);
    public List<Question> getAllQuestionsListBySubjectId(String subjectId);
    public boolean delete(String questionId);
    public Map getAllQuestionsListByCreateDesc(int page, int size);
    public Question editByAnswersList(String qId, QuestionRequest question);
    public List<Question> getRandomQuestionListBySubjectId(String subjectId);
    public Question editSubjectsAndTitle(String id,String subjectId, String  q, String  uz);
}
