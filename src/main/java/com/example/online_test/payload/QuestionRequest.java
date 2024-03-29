package com.example.online_test.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionRequest {
    private String questionUz;
    private String questionRu;
    private List<AnswerRequest> answers;
    private String subjectId;
}
