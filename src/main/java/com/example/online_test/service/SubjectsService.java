package com.example.online_test.service;

import com.example.online_test.entity.Subjects;
import com.example.online_test.payload.SubjectRequest;
import com.example.online_test.payload.SubjectRequestCreate;
import com.example.online_test.payload.SubjectResponse;

import java.util.List;
import java.util.Map;

public interface SubjectsService {
    public boolean create(SubjectRequestCreate subjectRequest);
    public List<SubjectResponse> subjectsList();
    public Map subjectsListByPage(int page, int size);
    public boolean edit(String id,SubjectRequest subjectRequest);
    public boolean delete(String subjectId);
    public SubjectResponse getOneById(String id);
    public List<SubjectResponse> getSubjectListByIds(String id1, String id2);
}
