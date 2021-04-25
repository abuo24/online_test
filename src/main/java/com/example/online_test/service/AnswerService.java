package com.example.online_test.service;

import com.example.online_test.entity.Answer;
import com.example.online_test.payload.AnswerRequest;

import java.util.List;

public interface AnswerService {

    public Answer create(Answer answer);

    public List<AnswerRequest> createAll(List<AnswerRequest> answers);
    public boolean delete(List<Answer> answers);
}
