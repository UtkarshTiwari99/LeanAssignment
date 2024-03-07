package com.utkarsh.leanassignment.dto;

import com.utkarsh.leanassignment.model.Option;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
public class TestResultDTO {

    private Date date;

    private Integer totalMarks;

    private String options;

    private String questions;

    private String timeTaken;

    public TestResultDTO(Date date, Integer totalMarks, String options, String questions, String timeTaken){
        this.date=date;
        this.totalMarks=totalMarks;
        this.options=options;
        this.questions=questions;
        this.options=options;
    }

}
