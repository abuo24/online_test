package com.example.online_test.loader;

import com.example.online_test.entity.Admin;
import com.example.online_test.entity.Role;
import com.example.online_test.entity.User;
import com.example.online_test.repository.AdminRepository;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.initialization-mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {

        if (initMode.equalsIgnoreCase("always")){

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        roleRepository.save(adminRole);

        Role mentorRole = new Role();
        mentorRole.setName("ROLE_MENTOR");
        roleRepository.save(mentorRole);

        User adminUser = new User();
        adminUser.setFirst_name("Admin");
        adminUser.setPassword(passwordEncoder.encode("admin123"));
        adminUser.setLast_name("Super admin");
        adminUser.setPhoneNumber("+998937641464");
        adminUser.setRoles(new ArrayList<Role>() {{
            add(adminRole);
            add(mentorRole);
        }});
        userRepository.save(adminUser);

        User mentorUser = new User();
        mentorUser.setLast_name("Super mentor");
        mentorUser.setPassword(passwordEncoder.encode("mentor123"));
        mentorUser.setFirst_name("Mentor");
        mentorUser.setPhoneNumber("+998932099924");
        mentorUser.setRoles(new ArrayList<Role>() {{
            add(mentorRole);
        }});
        userRepository.save(mentorUser);

        Role userRole=new Role();
        userRole.setName("ROLE_USER");
        roleRepository.save(userRole);

        User user=new User();
        user.setFirst_name("User");
        user.setLast_name("Super user");
        user.setPhoneNumber("+998901234567");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRoles(new ArrayList<Role>(){{
            add(userRole);
        }});
        userRepository.save(user);
    }}


}

