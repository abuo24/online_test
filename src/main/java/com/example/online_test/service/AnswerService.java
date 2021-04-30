package com.example.online_test.service;

import com.example.online_test.entity.Answer;
import com.example.online_test.entity.Question;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.payload.QuestionRequest;

import java.util.List;

public interface AnswerService {

    public Answer create(Answer answer);
    public Answer edit(String id, String answerUz, String answerRu);
    public boolean deleteById(String answers);

    public List<AnswerRequest> createAll(List<AnswerRequest> answers);

    public void delete(List<Answer> answers);
}
