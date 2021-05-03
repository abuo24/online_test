package com.example.online_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Teacher extends User {

    @Column
    private String telegram;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @Column
    private String bioUz;

    @Column
    private String bioRu;

}
