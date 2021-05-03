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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Blog implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String titleUz;

    @Column(nullable = false)
    private String titleRu;

    @Column(nullable = false, length = 100000)
    private String contentUz;

    @Column(nullable = false, length = 100000)
    private String contentRu;

    @OneToOne
    private Attachment attachment;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm:ss",timezone = "Asia/Tashkent")
    @Column(nullable = false, updatable = true)
    @UpdateTimestamp
    private Date updateAt;


}
