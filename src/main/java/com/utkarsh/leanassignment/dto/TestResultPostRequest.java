package com.utkarsh.leanassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultPostRequest {
    private String date;
    private String testId;
    private String userName;
}