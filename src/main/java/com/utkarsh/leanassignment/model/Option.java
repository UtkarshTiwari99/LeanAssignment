package com.utkarsh.leanassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@AllArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Integer op_number;

    private String  content;

    private Integer nextQuestion;

    public Option(Integer op_number, String content, Integer nextQuestion){
        this.op_number=op_number;
        this.content=content;
        this.nextQuestion=nextQuestion;
    }

}
