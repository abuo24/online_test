package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRequest {

    private String firstName;

    private String lastName;

    private String password;

    private String phoneNumber;

    private String bioUz;

    private String bioRu;

    private String facebook;
    private String instagram;
    private String telegram;
    private String hashId;


}
