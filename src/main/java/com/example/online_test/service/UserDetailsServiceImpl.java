package com.example.online_test.service;

import com.example.online_test.entity.User;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (byPhoneNumber.isPresent()){
            User user = byPhoneNumber.get();
            return new UserDetailsImpl(
                    user.getId(),
                    user.getFirst_name(),
                    user.getLast_name(),
                    user.getPassword(),
                    user.getPhoneNumber(),
                    user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
            );
        }
        throw new UsernameNotFoundException("User not found!");
    }
}
