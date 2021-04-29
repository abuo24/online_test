package com.example.online_test.payload;

import com.example.online_test.entity.Question;
import com.example.online_test.entity.Subjects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubjectRequest {

    private String nameUz;

    private String nameRu;

    private List<String> parentsFirst;

    private List<SubRequest> parentsSecond;

}
