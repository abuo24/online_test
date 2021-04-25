package com.example.online_test.service;

import com.example.online_test.entity.Answer;
import com.example.online_test.entity.Question;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.payload.QuestionRequest;
import com.example.online_test.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    AnswerService answerService;

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Override
    public Question create(QuestionRequest question) {
        try {
            Question question1 = new Question();
            if (question == null) {
                return null;
            }
            question1.setQuestion(question.getQuestion());
            question1.setSubjects(subjectsRepository.findById(question.getSubjectId()).get());
            if (question.getAnswers().isEmpty()) {
                return null;
            }
            List<AnswerRequest> answerRequests = answerService.createAll(question.getAnswers());
            List<Answer> answerList = new ArrayList<>();
            Answer answer = null;
            String id = "";
            answerRequests.forEach(item -> {
                if (item.isCorrect()) {
                    id.concat(item.getId());
                }
                answerList.add(answerRepository.getOne(item.getId()));
            });
            question1.setAnswer(answerList);
            question1.setCorrectAnswer(answerRepository.getOne(id));
            return questionRepository.save(question1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Question> getAllQuestionsListBySubjectId(String subjectId) {
        try {
            return questionRepository.findAllBySubjectsId(subjectId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Page<Question> getRandomQuestionListBySubjectId(String subjectId) {
        try {
            Long qty = questionRepository.countAllBySubjectsId(subjectId);
            int idx = (int) (Math.random() * qty/30);
            Pageable paging = PageRequest.of(idx, 30);
            Page<Question> questionPage = questionRepository.findAllBySubjectsId(subjectId, paging);
            return questionPage;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean delete(String questionId) {
        try {
            Question question = questionRepository.findById(questionId).get();
            if (question == null) {
                return false;
            }
            answerService.delete(question.getAnswer());
            questionRepository.deleteById(question.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
