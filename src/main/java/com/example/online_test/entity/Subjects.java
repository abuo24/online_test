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

@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Subjects implements Serializable {

    @Id
    @GenericGenerator(name = "uuid",strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    private String nameUz;

    @Column(nullable = false)
    private String nameRu;

    @ManyToMany
    @JsonIgnore
    private List<SubHelp> parentsSecond;

    @ManyToMany
    @JsonIgnore
    private List<Subjects> parentsFirst;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameUz() {
        return nameUz;
    }

    public void setNameUz(String nameUz) {
        this.nameUz = nameUz;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public List<SubHelp> getParentsSecond() {
        return parentsSecond;
    }

    public void setParentsSecond(List<SubHelp> parentsSecond) {
        this.parentsSecond = parentsSecond;
    }

    @JsonIgnore
    public List<Subjects> getParentsFirst() {
        return parentsFirst;
    }

    public void setParentsFirst(List<Subjects> parentsFirst) {
        this.parentsFirst = parentsFirst;
    }


}
