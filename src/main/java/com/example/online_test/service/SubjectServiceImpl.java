package com.example.online_test.service;

import com.example.online_test.entity.Answer;
import com.example.online_test.entity.Question;
import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.AnswerRequest;
import com.example.online_test.payload.QuestionRequest;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.repository.AnswerRepository;
import com.example.online_test.repository.QuestionRepository;
import com.example.online_test.repository.SubHelpRepository;
import com.example.online_test.repository.SubjectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectsService {

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    SubHelpRepository subHelpRepository;

    @Override
    public Subjects create(SubjectRequest subjectRequest) {
        try {
            Subjects subject = new Subjects();
            SubHelp subHelp = new SubHelp();
            if (subjectRequest == null) {
                return null;
            }
            subject.setNameRu(subjectRequest.getNameRu());
            subject.setNameUz(subjectRequest.getNameUz());
            subject.setParentsFirst(subjectsRepository.findAllById(subjectRequest.getParentsFirst()));
            List<SubHelp> subHelps = new ArrayList<>();
            subjectRequest.getParentsSecond().forEach(item -> {
                subHelp.setParentsId(item.getId());
                subHelp.setChildless(item.getChildren());
                subHelps.add(subHelpRepository.save(subHelp));
            });
            subject.setParentsSecond(subHelps);
            return subjectsRepository.save(subject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Subjects edit(String id, SubjectRequest subjectRequest) {
        try {
            Subjects subject = subjectsRepository.getOne(id);
            if (subject == null) {
                return null;
            }
            SubHelp subHelp = new SubHelp();
            if (subjectRequest == null) {
                return null;
            }
            subject.setNameRu(subjectRequest.getNameRu());
            subject.setNameUz(subjectRequest.getNameUz());
            subject.setParentsFirst(subjectsRepository.findAllById(subjectRequest.getParentsFirst()));
            List<SubHelp> subHelps = new ArrayList<>();
            subjectRequest.getParentsSecond().forEach(item -> {
                subHelp.setParentsId(item.getId());
                subHelp.setChildless(item.getChildren());
                subHelps.add(subHelpRepository.save(subHelp));
            });
            subject.setParentsSecond(subHelps);
            return subjectsRepository.save(subject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Subjects> subjectsList() {
        try {
            return subjectsRepository.findAll();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Subjects getOneById(String id) {
        try {
            return subjectsRepository.findById(id).get();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Subjects> getSubjectListByIds(String id1, String id2) {
        try {
            Subjects subjects = subjectsRepository.getOne(id1);
            if (subjects==null){
                return null;
            }
            List<Subjects> subjectsList = new ArrayList<>();
            List<SubHelp> subHelps = subjects.getParentsSecond();
            subHelps.forEach(item->{
                if (item.getParentsId().equals(id2)){
                    subjectsList.addAll(subjectsRepository.findAllById(item.getChildless()));
                }
            });
            return subjectsList;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean delete(String subjectId) {
        try {
            Subjects subjects = subjectsRepository.findById(subjectId).get();
            if (subjects == null) {
                return false;
            }
            subjectsRepository.deleteById(subjects.getId());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
