package com.utkarsh.leanassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private Integer questionNumber;

    @Column
    private String statement;

    @Column
    private Integer marks;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name="question_id")
    private Collection<Option> options=new ArrayList<Option>();

    public Question(Integer questionNumber,String statement,Integer marks,List<Option> options){
        this.questionNumber=questionNumber;
        this.statement=statement;
        this.marks=marks;
        this.options=options;
    }

}