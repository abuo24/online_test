package com.example.online_test.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {
    private String titleUz;
    private String titleRu;
    private String id;
    private boolean isCorrect;
}
