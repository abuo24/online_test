package com.example.online_test.serviceImpl;

import com.example.online_test.entity.History;
import com.example.online_test.entity.Teacher;
import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.payload.ReqUser;
import com.example.online_test.payload.TeacherRequest;
import com.example.online_test.repository.*;
import com.example.online_test.service.TeacherService;
import com.example.online_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Override
    public Map getAllTeachers(int page, int size) {
        List<Teacher> tutorials = new ArrayList<>();
        Pageable paging = PageRequest.of(page, size);
        Page<Teacher> pageTuts = teacherRepository.findAllByOrderByCreateAtDesc(paging);
        tutorials = pageTuts.getContent();
        Map<String, Object> response = new HashMap<>();
        response.put("teachers", tutorials);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());
        return response;
    }

    @Override
    public boolean create(TeacherRequest reqUser) {
        try {
            if (reqUser == null) {
                return false;
            }
            Teacher user = new Teacher();
            user.setFirst_name(reqUser.getFirstName());
            user.setLast_name(reqUser.getLastName());
            user.setPhoneNumber(reqUser.getPhoneNumber());
            user.setPassword(passwordEncoder.encode(reqUser.getPassword()));
            user.setGroups(null);
            user.setBioRu(reqUser.getBioRu());
            user.setBioUz(reqUser.getBioUz());
            user.setFacebook(reqUser.getFacebook());
            user.setInstagram(reqUser.getInstagram());
            user.setTelegram(reqUser.getTelegram());
            user.setAvatar(attachmentRepository.findByHashId(reqUser.getHashId()));
            user.setRoles(roleRepository.findAllByName("ROLE_USER"));
            return teacherRepository.save(user) != null;
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean editUser(TeacherRequest reqUser, String id) {

        try {
            Optional<Teacher> byId = teacherRepository.findById(id);
            if (byId.isPresent()) {
                Teacher user = byId.get();
                user.setId(id);
                user.setFirst_name(reqUser.getFirstName());
                user.setLast_name(reqUser.getLastName());
                user.setPassword(reqUser.getPassword());
                user.setPhoneNumber(reqUser.getPhoneNumber());
                user.setGroups(null);
                user.setBioRu(reqUser.getBioRu());
                user.setBioUz(reqUser.getBioUz());
                user.setFacebook(reqUser.getFacebook());
                user.setInstagram(reqUser.getInstagram());
                user.setTelegram(reqUser.getTelegram());
                user.setAvatar(attachmentRepository.findByHashId(reqUser.getHashId()));
                return teacherRepository.save(user) != null;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public boolean deleteUser(String id) {
        try {
            Optional<Teacher> byId = teacherRepository.findById(id);
            if (!byId.isPresent()) {
                return false;
            }
            teacherRepository.deleteById(id);
            return true;
        } catch (Exception e) {

        }
        return false;
    }


}
