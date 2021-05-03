package com.example.online_test.serviceImpl;

import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import com.example.online_test.entity.Teacher;
import com.example.online_test.payload.SubRequest;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.payload.SubjectRequestCreate;
import com.example.online_test.repository.QuestionRepository;
import com.example.online_test.repository.SubHelpRepository;
import com.example.online_test.repository.SubjectsRepository;
import com.example.online_test.service.SubjectsService;
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
public class SubjectServiceImpl implements SubjectsService {

    @Autowired
    SubjectsRepository subjectsRepository;

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    SubHelpRepository subHelpRepository;

    @Override
    public Subjects create(SubjectRequestCreate subjectRequest) {
        try {
            Subjects subject = new Subjects();
            SubHelp subHelp = new SubHelp();
            if (subjectRequest == null) {
                return null;
            }
            subject.setNameRu(subjectRequest.getNameRu());
            subject.setNameUz(subjectRequest.getNameUz());
            subject.setParentsFirst(null);
            subject.setParentsSecond(null);
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
            SubHelp subHelp = null;
            if (subjectRequest == null) {
                return null;
            }
            subject.setNameRu(subjectRequest.getNameRu());
            subject.setNameUz(subjectRequest.getNameUz());
            subject.setParentsFirst(subjectsRepository.findAllById(subjectRequest.getParentsFirst()));
            List<SubHelp> subHelps = new ArrayList<>();
            for (SubRequest item : subjectRequest.getParentsSecond()) {
                subHelp = new SubHelp();
                subHelp.setParentsId(item.getId());
                subHelp.setChildless(item.getChildren());
                subHelps.add(subHelpRepository.save(subHelp));
            }
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
    public Map subjectsListByPage(int page,int size) {
        try {
            List<Subjects> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Subjects> pageTuts = subjectsRepository.findAll(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("subjects", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
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
