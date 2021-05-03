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
import javax.security.auth.Subject;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity(name = "course")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Course implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String titleUz;

    @Column(nullable = false)
    private String titleRu;

    private Integer durationTime;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "course_subjects",
//            joinColumns = @JoinColumn(name = "course_id"),
//            inverseJoinColumns = @JoinColumn(name = "subject_id"))
//    private List<Subjects> subjects;

    @Column(nullable = false)
    private String descriptionUz;

    @Column(nullable = false)
    private String descriptionRu;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_id")
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
