package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Answer;
import com.example.online_test.entity.Question;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.payload.QuestionRequest;
import com.example.online_test.repository.AnswerRepository;
import com.example.online_test.repository.QuestionRepository;
import com.example.online_test.repository.SubjectsRepository;
import com.example.online_test.service.AnswerService;
import com.example.online_test.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            question1.setQuestionUz(question.getQuestionUz());
            question1.setQuestionRu(question.getQuestionRu());
            question1.setSubjects(subjectsRepository.findById(question.getSubjectId()).get());
            if (question.getAnswers().isEmpty()) {
                return null;
            }
            List<AnswerRequest> answerRequests = answerService.createAll(question.getAnswers());
            List<Answer> answerList = new ArrayList<>();
            Answer answer = null;
            String id = "";
            for (int i = 0; i < answerRequests.size(); i++) {
                if (answerRequests.get(i).isCorrect()) {
                    id=answerRequests.get(i).getId();
                }
                answerList.add(answerRepository.getOne(answerRequests.get(i).getId()));
            }
            question1.setAnswer(answerList);
            question1.setCorrectAnswerId(id);
            return questionRepository.save(question1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    @Override
    public Question editByAnswersList(String qId, QuestionRequest question) {
        try {
            Question question1 = questionRepository.getOne(qId);
            if (question == null) {
                return null;
            }
            question1.setQuestionRu(question.getQuestionRu());
            question1.setQuestionUz(question.getQuestionUz());
            question1.setSubjects(subjectsRepository.findById(question.getSubjectId()).get());
            if (question.getAnswers().isEmpty()) {
                return null;
            }
            List<AnswerRequest> answerRequests = answerService.createAll(question.getAnswers());
            List<Answer> answerList = new ArrayList<>();
            Answer answer = null;
            String id = "";
            for (int i = 0; i < answerRequests.size(); i++) {
                if (answerRequests.get(i).isCorrect()) {
                    id=answerRequests.get(i).getId();
                }
                answerList.add(answerRepository.getOne(answerRequests.get(i).getId()));
            }
            question1.setAnswer(answerList);
            question1.setCorrectAnswerId(id);
            return questionRepository.save(question1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Question editSubjectsAndTitle(String id, String subjectId, String  q, String  uz) {
        try {
            Question question1 = questionRepository.getOne(id);
            if (question1 == null) {
                return null;
            }
            question1.setQuestionRu(q);
            question1.setQuestionUz(uz);
            question1.setSubjects(subjectsRepository.findById(subjectId).get());
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
    public Map getAllQuestionsListByCreateDesc(int page, int size) {
        try {
            List<Question> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Question> pageTuts = questionRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("questions", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Question> getRandomQuestionListBySubjectId(String subjectId) {
        try {
            Long qty = questionRepository.countAllBySubjectsId(subjectId);
            int idx = (int) (Math.random() * qty/30);
            Pageable paging = PageRequest.of(idx, 30);
            List<Question> questionPage = questionRepository.findAllBySubjectsId(subjectId, paging);
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
