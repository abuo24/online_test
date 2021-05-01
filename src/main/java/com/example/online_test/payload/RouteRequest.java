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

    private String subjectFirst;

    private String subjectSecond;

    private String subjectThird;
}
