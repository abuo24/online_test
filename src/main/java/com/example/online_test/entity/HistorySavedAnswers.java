package com.example.online_test.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HistorySavedAnswers implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    private String questionId;

    private String correctAnswerId;

    private String selectedId;

}
