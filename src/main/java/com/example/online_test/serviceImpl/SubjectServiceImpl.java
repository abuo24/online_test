package com.example.online_test.serviceImpl;

import com.example.online_test.entity.SubHelp;
import com.example.online_test.entity.Subjects;
import com.example.online_test.entity.Teacher;
import com.example.online_test.payload.*;
import com.example.online_test.repository.QuestionRepository;
import com.example.online_test.repository.SubHelpRepository;
import com.example.online_test.repository.SubjectsRepository;
import com.example.online_test.service.SubjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.security.auth.Subject;
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
    public boolean create(SubjectRequestCreate subjectRequest) {
        try {
            Subjects subject = new Subjects();
            SubHelp subHelp = new SubHelp();
            if (subjectRequest == null) {
                return false;
            }
            subject.setNameRu(subjectRequest.getNameRu());
            subject.setNameUz(subjectRequest.getNameUz());
            subject.setParentsFirst(null);
            subject.setParentsSecond(null);
            return subjectsRepository.save(subject) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean edit(String id, SubjectRequest subjectRequest) {
        try {
            Subjects subject = subjectsRepository.getOne(id);
            if (subject == null) {
                return false;
            }
            SubHelp subHelp = null;
            if (subjectRequest == null) {
                return false;
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
            return subjectsRepository.save(subject) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public List<SubjectResponse> subjectsList() {
        try {
            List<Subjects> tutorials = subjectsRepository.findAll();
            SubjectResponse subjectResponse = new SubjectResponse();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<SubjectResponse> subjectResponseList = new ArrayList<>();
            List<SubjectResHelp> subjectResHelpList = new ArrayList<>();
            for (int i = 0; i < tutorials.size(); i++) {
                subjectResponse = new SubjectResponse();
                subjectResponse.setId(tutorials.get(i).getId());
                subjectResponse.setNameRu(tutorials.get(i).getNameRu());
                subjectResponse.setNameUz(tutorials.get(i).getNameUz());
                subjectResHelpList = new ArrayList<>();
                for (int j = 0; j < tutorials.get(i).getParentsFirst().size(); j++) {
                    subjectResHelp = new SubjectResHelp();
                    subjectResHelp.setId(tutorials.get(i).getParentsFirst().get(j).getId());
                    subjectResHelp.setNameRu(tutorials.get(i).getParentsFirst().get(j).getNameRu());
                    subjectResHelp.setNameUz(tutorials.get(i).getParentsFirst().get(j).getNameUz());
                    subjectResHelpList.add(subjectResHelp);
                }
                subjectResponse.setParentsFirst(subjectResHelpList);
                subjectResponseList.add(subjectResponse);
            }
            return subjectResponseList;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Map subjectsListByPage(int page, int size) {
        try {
            List<Subjects> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Subjects> pageTuts = subjectsRepository.findAll(paging);
            tutorials = pageTuts.getContent();
            SubjectResponse subjectResponse = new SubjectResponse();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<SubjectResponse> subjectResponseList = new ArrayList<>();
            List<SubjectResHelp> subjectResHelpList = new ArrayList<>();
            for (int i = 0; i < tutorials.size(); i++) {
                subjectResponse = new SubjectResponse();
                subjectResponse.setId(tutorials.get(i).getId());
                subjectResponse.setNameRu(tutorials.get(i).getNameRu());
                subjectResponse.setNameUz(tutorials.get(i).getNameUz());
                subjectResHelpList = new ArrayList<>();
                for (int j = 0; j < tutorials.get(i).getParentsFirst().size(); j++) {
                    subjectResHelp = new SubjectResHelp();
                    subjectResHelp.setId(tutorials.get(i).getParentsFirst().get(j).getId());
                    subjectResHelp.setNameRu(tutorials.get(i).getParentsFirst().get(j).getNameRu());
                    subjectResHelp.setNameUz(tutorials.get(i).getParentsFirst().get(j).getNameUz());
                    subjectResHelpList.add(subjectResHelp);
                }
                subjectResponse.setParentsFirst(subjectResHelpList);
                subjectResponseList.add(subjectResponse);
            }
            Map<String, Object> response = new HashMap<>();
            response.put("subjects", subjectResponseList);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public SubjectResponse getOneById(String id) {
        try {
            Subjects tutorials = subjectsRepository.getOne(id);
            SubjectResponse subjectResponse = new SubjectResponse();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<SubjectResHelp> subjectResHelpList = new ArrayList<>();
            subjectResponse.setId(tutorials.getId());
            subjectResponse.setNameRu(tutorials.getNameRu());
            subjectResponse.setNameUz(tutorials.getNameUz());
            for (int j = 0; j < tutorials.getParentsFirst().size(); j++) {
                subjectResHelp = new SubjectResHelp();
                subjectResHelp.setId(tutorials.getParentsFirst().get(j).getId());
                subjectResHelp.setNameRu(tutorials.getParentsFirst().get(j).getNameRu());
                subjectResHelp.setNameUz(tutorials.getParentsFirst().get(j).getNameUz());
                subjectResHelpList.add(subjectResHelp);
            }
            subjectResponse.setParentsFirst(subjectResHelpList);
            return subjectResponse;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<SubjectResponse> getSubjectListByIds(String id1, String id2) {
        try {
            Subjects subjects = subjectsRepository.getOne(id1);
            if (subjects == null) {
                return null;
            }
            List<Subjects> tutorials = new ArrayList<>();
            List<SubHelp> subHelps = subjects.getParentsSecond();
            subHelps.forEach(item -> {
                if (item.getParentsId().equals(id2)) {
                    tutorials.addAll(subjectsRepository.findAllById(item.getChildless()));
                }
            });
            SubjectResponse subjectResponse = new SubjectResponse();
            SubjectResHelp subjectResHelp = new SubjectResHelp();
            List<SubjectResponse> subjectResponseList = new ArrayList<>();
            List<SubjectResHelp> subjectResHelpList = new ArrayList<>();
            for (int i = 0; i < tutorials.size(); i++) {
                subjectResponse = new SubjectResponse();
                subjectResHelpList = new ArrayList<>();
                subjectResponse.setId(tutorials.get(i).getId());
                subjectResponse.setNameRu(tutorials.get(i).getNameRu());
                subjectResponse.setNameUz(tutorials.get(i).getNameUz());
                for (int j = 0; j < tutorials.get(i).getParentsFirst().size(); j++) {
                    subjectResHelp = new SubjectResHelp();
                    subjectResHelp.setId(tutorials.get(i).getParentsFirst().get(j).getId());
                    subjectResHelp.setNameRu(tutorials.get(i).getParentsFirst().get(j).getNameRu());
                    subjectResHelp.setNameUz(tutorials.get(i).getParentsFirst().get(j).getNameUz());
                    subjectResHelpList.add(subjectResHelp);
                }
                subjectResponse.setParentsFirst(subjectResHelpList);
                subjectResponseList.add(subjectResponse);
            }

            return subjectResponseList;
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
