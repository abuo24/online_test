package com.example.online_test.service;

import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.SubjectRequest;

import java.util.List;

public interface SubjectsService {
    public Subjects create(SubjectRequest subjectRequest);
    public List<Subjects> subjectsList();
    public Subjects edit(String id,SubjectRequest subjectRequest);
    public boolean delete(String subjectId);
    public Subjects getOneById(String id);
    public List<Subjects> getSubjectListByIds(String id1, String id2);
}
