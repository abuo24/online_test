package com.example.online_test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Teacher extends User {

    @Column
    private String telegram;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @Column
    private String bioUz;

    @OneToOne
    private Attachment avatar;

    @Column
    private String bioRu;

}
