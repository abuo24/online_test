package com.example.online_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Question implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String question;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_answerss",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "answers_id"))
    private List<Answer> answer;

    @OneToOne
    private Answer correctAnswer;

    @ManyToOne
    private Subjects subjects;
}
