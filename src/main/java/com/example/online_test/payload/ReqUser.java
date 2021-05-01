package com.example.online_test.payload;

import com.example.online_test.entity.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqUser {

    private String first_name;

    private String last_name;

    private String password;

    private String phoneNumber;

    private String groupId;

}
