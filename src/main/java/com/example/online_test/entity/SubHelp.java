package com.example.online_test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SubHelp implements Serializable {

    @Id
    @JsonIgnore
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String parentsId;

    @ElementCollection
    @CollectionTable(name = "list", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "list")
    private List<String> childless;
}
