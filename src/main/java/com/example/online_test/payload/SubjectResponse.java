package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectResponse {

    private String id;
    private String nameUz;

    private String nameRu;

    private List<SubjectResHelp> parentsFirst;

}
