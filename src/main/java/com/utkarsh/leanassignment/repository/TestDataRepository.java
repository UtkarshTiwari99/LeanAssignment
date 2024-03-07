package com.utkarsh.leanassignment.repository;

import com.utkarsh.leanassignment.model.TestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDataRepository extends JpaRepository<TestData,String> {
  @Query(value = "SELECT * FROM test_data inner join question on test_data.test_id = question.test_qa where question.question_number = ?1 and test_data.test_id = ?2",nativeQuery = true)
    List<TestData> findQuestions(Integer questionNumber,String testId);

}