package com.example.online_test.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class History implements Serializable  {
    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @OneToOne
    private User user;

    @OneToOne
    private Blok blok;

    private Double ballAll;

    private Double percentAll;

    private int countAll;

    private Double ballFirst;

    private Double percentFirst;

    private int countFirst;

    private Double ballSecond;

    private Double percentSecond;

    private int countSecond;

    private Double ballThird;

    private String spentTime;

    @OneToMany
    private List<HistorySavedAnswers> historySavedAnswers;

    private int countThird;

    private Double percentThird;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    @Column(nullable = false, updatable = true)
    @UpdateTimestamp
    private Date updateAt;
}


