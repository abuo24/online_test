package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {


    private String token;

    private String id;

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private List<String> roleList;


}
