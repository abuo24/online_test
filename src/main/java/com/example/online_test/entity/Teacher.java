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

    @Column()
    private String social;
}
