package com.example.online_test.controller;

import com.example.online_test.entity.Role;
import com.example.online_test.entity.User;
import com.example.online_test.model.Result;
import com.example.online_test.model.ResultSucces;
import com.example.online_test.payload.JwtResponse;
import com.example.online_test.payload.LoginRequest;
import com.example.online_test.repository.RoleRepository;
import com.example.online_test.repository.UserRepository;

import com.example.online_test.security.JwtTokenProvider;

import com.example.online_test.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "*", maxAge = 3600)
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
    private JwtTokenProvider jwtProvider;

    @PostMapping("/login")
    public HttpEntity<?> login(@RequestBody LoginRequest loginVM) {
        Optional<User> user = userRepository.findByPhoneNumber(loginVM.getPhoneNumber());
        if (!user.isPresent()) {
            return new ResponseEntity<>(new Result(false, "User not found!"), HttpStatus.NOT_FOUND);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginVM.getPhoneNumber(), loginVM.getPassword()));
            Set<Role> roleList = new HashSet<>(user.get().getRoles());
            String token = jwtProvider.createToken(user.get().getPhoneNumber(),roleList);
            Map<Object, Object> map = new HashMap<>();
            map.put("succes", true);
            map.put("phoneNumber", user.get().getPhoneNumber());
            map.put("token", token);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            return new ResponseEntity<>(new Result(false, e.getLocalizedMessage()), BAD_REQUEST);
        }
    }

    @GetMapping("/me")
    public ResponseEntity getUser(HttpServletRequest request) {
      Optional<User> user = userRepository.findByPhoneNumber(jwtProvider.getUser(jwtProvider.resolveToken(request)));
        return user.isPresent()? ResponseEntity.ok(new ResultSucces(true, user.get())) : (new ResponseEntity(new Result(false, "token is invalid"), BAD_REQUEST));
    }

}
