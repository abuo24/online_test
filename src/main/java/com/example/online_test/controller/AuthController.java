package com.example.online_test.controller;

import com.example.online_test.entity.Role;
import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.model.ResultSucces;
import com.example.online_test.payload.JwtResponse;
import com.example.online_test.payload.LoginRequest;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.repository.UserRepository;
import com.example.online_test.security.JwtUtils;
import com.example.online_test.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getPhoneNumber(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt=jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles=userDetails.getAuthorities().stream().map(
                item -> item.getAuthority()).collect(Collectors.toList());
        return new ResponseEntity<>(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getFirstName(),
                userDetails.getLastName(),
                userDetails.getPassword(),
                userDetails.getPhoneNumber(),
                roles
        ), HttpStatus.OK);
    }

//
//    @GetMapping("/getme")
//    public ResponseEntity getUser(HttpServletRequest request) {
//        User user = userRepository.findByPhoneNumber(jwtProvider.getUser(jwtProvider.resolveToken(request)));
//        return user!=null? ResponseEntity.ok(new ResultSucces(true, user)) : (new ResponseEntity(new Result(false, "token is invalid"), BAD_REQUEST));
//    }


}
