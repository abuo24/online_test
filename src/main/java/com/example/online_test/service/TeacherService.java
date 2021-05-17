package com.example.online_test.service;

import com.example.online_test.entity.Teacher;
import com.example.online_test.payload.TeacherRequest;

import java.util.Map;

public interface TeacherService {
    public Teacher getOne(String id);
    public boolean deleteUser(String id);
    public boolean editUser(TeacherRequest reqUser, String id);
    public boolean create(TeacherRequest reqUser);
    public Map getAllTeachers(int page, int size);
}
