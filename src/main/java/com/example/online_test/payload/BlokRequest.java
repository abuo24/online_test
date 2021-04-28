package com.example.online_test.payload;

import com.example.online_test.entity.Subjects;
import com.example.online_test.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlokRequest {

    private String blokFirstId;

    private String blokSecondId;

    private String blokThirdId;

}
