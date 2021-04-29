package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Answer;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.repository.AnswerRepository;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
            answer.setTitleRu(answer.getTitleRu());
            answer.setTitleUz(answer.getTitleUz());
            return answerRepository.save(answer);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public Answer edit(String id, String answer) {
        try {
            Answer answer1 =answerRepository.getOne(id);
            if (answer == null) {
                return null;
            }
            if (answer1 == null) {
                return null;
            }
            answer1.setTitleUz(answer);
            answer1.setTitleRu(answer);
            return answerRepository.save(answer1);
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

            for(int i = 0; i < answers.size(); i++) {
                answerRequest = new AnswerRequest();
                answer2 = new Answer();
                answer2.setTitleRu(answers.get(i).getTitleRu());
                answer2.setTitleUz(answers.get(i).getTitleUz());
                answer1 = create(answer2);
                if (answer1 != null) {
                    if (answers.get(i).isCorrect()) {
                        answerRequest.setCorrect(true);
                    } else {
                        answerRequest.setCorrect(false);
                    }
                    answerRequest.setTitleUz(answers.get(i).getTitleUz());
                    answerRequest.setTitleRu(answers.get(i).getTitleRu());
                    answerRequest.setId(answer1.getId());
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
    public void delete(List<Answer> answers) {
        try {
            answerRepository.deleteAll(answers);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public boolean deleteById(String answers) {
        try {
            answerRepository.deleteById(answers);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
