package com.example.online_test.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VerifingRequest {

    private List<VerifyingAnswers> subjectFirstAnswersList;
    private List<VerifyingAnswers> subjectSecondAnswersList;
    private List<VerifyingAnswers> subjectThirdAnswersList;
}
