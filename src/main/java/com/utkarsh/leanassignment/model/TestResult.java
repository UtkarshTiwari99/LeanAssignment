package com.utkarsh.leanassignment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResult{

    @Id
    private Date date;

    private String timeTaken;

    private Integer totalMarks;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name="test_da")
    private TestData testData;

    private String questions;

    private String options;

}