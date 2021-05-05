package com.example.online_test.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponse {

    private String name;
    private String id;

    private int code;

    private SubjectResHelp subjectFirst;

    private SubjectResHelp subjectSecond;

    private SubjectResHelp subjectThird;

}
