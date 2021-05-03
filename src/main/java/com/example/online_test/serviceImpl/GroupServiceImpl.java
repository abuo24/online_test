package com.example.online_test.serviceImpl;

import com.example.online_test.entity.Groups;
import com.example.online_test.entity.User;
import com.example.online_test.payload.ReqGroup;
import com.example.online_test.repository.CourseRepository;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupsRepository groupsRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Map getAllByPages(int page, int size) {
        try {
            List<Groups> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Groups> pageTuts = groupsRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("groups", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    @Override
    public Map getUserByGroupId(String id, int page, int size) {

        try {
            List<User> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<User> pageTuts = userRepository.findAllByGroupsId(id, paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("users", tutorials);
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
    public boolean addGroups(ReqGroup reqGroup) {
        try {
            Groups groups = new Groups();
            groups.setName(reqGroup.getName());
            groups.setCourse(courseRepository.getOne(reqGroup.getCourseId()));
            return groupsRepository.save(groups) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean editGroup(ReqGroup reqGroup, String id) {
        try {
            Groups byId = groupsRepository.findById(id).get();
            if (byId==null) {
                return false;
            }
            Groups groups = byId;
            groups.setId(id);
            groups.setCourse(courseRepository.getOne(reqGroup.getCourseId()));
            groups.setName(reqGroup.getName());
            return groupsRepository.save(groups) != null;
        }catch (Exception e){
            e.getMessage();
        }
        return false;
    }

    @Override
    public boolean deleteGroup(String id) {
        try {
            Optional<Groups> byId = groupsRepository.findById(id);
            if (!byId.isPresent()) {
                return false;
            }
            groupsRepository.delete(byId.get());
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
