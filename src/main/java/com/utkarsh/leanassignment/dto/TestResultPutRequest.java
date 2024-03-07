package com.utkarsh.leanassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.query.Param;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultPutRequest {
    String append_questions;
    String append_options;
    String testId;
    Integer marks;
}
