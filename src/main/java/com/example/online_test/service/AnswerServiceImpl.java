package com.example.online_test.service;

import com.example.online_test.entity.Answer;
import com.example.online_test.entity.User;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.repository.AnswerRepository;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Override
    public Answer create(Answer answer) {
        try {
            Answer answer1 = new Answer();
            if (answer == null) {
                return null;
            }
            answer.setTitle(answer.getTitle());
            return answerRepository.save(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<AnswerRequest> createAll(List<AnswerRequest> answers) {
        try {
            Answer answer1 = new Answer();
            Answer answer2 = new Answer();
            AnswerRequest answerRequest = new AnswerRequest();
            if (answers.isEmpty()) {
                return null;
            }
            List<AnswerRequest> list = new ArrayList<>();

            for (int i = 0; i < answers.size(); i++) {
                answer2.setTitle(answers.get(i).getTitle());
                answer1 = create(answer2);
                if (answer1 != null) {
                    if (answers.get(i).isCorrect()) {
                        answerRequest.setCorrect(true);
                    }
                    answerRequest.setId(answers.get(i).getId());
                    list.add(answerRequest);
                }
            }
            return list;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(List<Answer> answers) {
        try {
            answerRepository.deleteAll(answers);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
