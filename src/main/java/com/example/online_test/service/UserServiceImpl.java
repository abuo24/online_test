package com.example.online_test.service;

import com.example.online_test.entity.Role;
import com.example.online_test.entity.User;
import com.example.online_test.repository.GroupsRepository;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public boolean create(User user, String groupId) {
        try {
            User user1 = new User();
            if (user==null){
                return false;
            }
            List<Role> roles=new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_USER"));
            user1.setFirst_name(user.getFirst_name());
            user1.setLast_name(user.getLast_name());
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
            user1.setPhoneNumber(user1.getPhoneNumber());
            user1.setRoles(roles);
            user1.setGroups(groupsRepository.findById(groupId).get());
            return userRepository.save(user1) != null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
