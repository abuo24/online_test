package com.example.online_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Answer implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false, length = 10000)
    private String title;

}
