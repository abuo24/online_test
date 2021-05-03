package com.example.online_test.service;

import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.payload.SubjectRequestCreate;

import java.util.List;
import java.util.Map;

public interface SubjectsService {
    public Subjects create(SubjectRequestCreate subjectRequest);
    public List<Subjects> subjectsList();
    public Map subjectsListByPage(int page, int size);
    public Subjects edit(String id,SubjectRequest subjectRequest);
    public boolean delete(String subjectId);
    public Subjects getOneById(String id);
    public List<Subjects> getSubjectListByIds(String id1, String id2);
}
