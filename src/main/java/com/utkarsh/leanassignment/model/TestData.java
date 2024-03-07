package com.utkarsh.leanassignment.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestData {

    @Id
    private String testId;

    @OneToMany(
    orphanRemoval = true,
    cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name="test_qa")
    private List<Question> questions;

}