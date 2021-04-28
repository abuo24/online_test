package com.example.online_test.payload;


import com.example.online_test.entity.Subjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteRequest {
    private String name;

    private Subjects subjectFirst;

    private Subjects subjectSecond;

    private Subjects subjectThird;

    private String universityId;
}
