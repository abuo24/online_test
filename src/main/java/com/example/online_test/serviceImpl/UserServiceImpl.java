package com.example.online_test.serviceImpl;

import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.payload.ReqUser;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    GroupsRepository groupsRepository;

    @Override
    public Map getAllUsersByPagealable(int page, int size) {
        try {
            List<User> tutorials = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<User> pageTuts = userRepository.findAllByOrderByCreateAtDesc(paging);
            tutorials = pageTuts.getContent();
            Map<String, Object> response = new HashMap<>();
            response.put("users", tutorials);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());
            return response;
        }catch (Exception e){

        }
        return null;
    }
    @Override
    public Result create(ReqUser reqUser) {
        if (reqUser==null){
            return new Result(false, "Error user created!");
        }
        User user=new User();
        user.setFirst_name(reqUser.getFirst_name());
        user.setLast_name(reqUser.getLast_name());
        user.setPhoneNumber(reqUser.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(reqUser.getPassword()));
        user.setGroups(groupsRepository.getOne(reqUser.getGroupId()));
        user.setRoles(roleRepository.findAllByName("ROLE_USER"));
        userRepository.save(user);
        return new Result(true, "Successfully created user");
    }
    @Override
    public Result editUser(ReqUser reqUser, String id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()){
            User user = byId.get();
            user.setId(id);
            user.setFirst_name(reqUser.getFirst_name());
            user.setLast_name(reqUser.getLast_name());
            user.setPassword(reqUser.getPassword());
            user.setPhoneNumber(reqUser.getPhoneNumber());
            user.setGroups(groupsRepository.getOne(reqUser.getGroupId()));
            userRepository.save(user);
            return new Result(true, "Successfully edited user");
        }
        return new Result(false, "User not found!");
    }

    @Override
    public Result deleteUser(String id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()){
            return new Result(false, "User not found");
        }
        userRepository.deleteById(id);
        return new Result(true, "Successfully deleted user");
    }


}
