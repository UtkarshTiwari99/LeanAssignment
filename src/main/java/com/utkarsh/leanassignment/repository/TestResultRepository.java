package com.utkarsh.leanassignment.repository;

import com.utkarsh.leanassignment.dto.TestResultDTO;
import com.utkarsh.leanassignment.model.TestResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface TestResultRepository extends PagingAndSortingRepository<TestResult, Time> {

//    @Query("UPDATE TestResult SET questions = CONCAT(questions, :appendQuestions),options = CONCAT(options, :appendOptions) WHERE testData.testId = :testId AND user.username = :userName")
    @Modifying
    @Transactional
    @Query("UPDATE TestResult tr SET tr.questions = CONCAT(tr.questions, :appendQuestions), tr.options = CONCAT(tr.options, :appendOptions) WHERE tr.date = ( SELECT MAX(t2.date) FROM TestResult t2 WHERE t2.testData.testId = :testId AND t2.user.username = :userName ) AND tr.testData.testId = :testId AND tr.user.username = :userName")
    void updateBySQL(@Param("appendQuestions") String append_questions,@Param("appendOptions") String append_options,@Param("testId") String testId,@Param("userName") String userName);

    @Modifying
    @Transactional
    @Query(value = "insert into test_result (date, time_taken, total_marks, test_da, user_id, questions, options) VALUES (?1 ,'', 0,?4,?5,'','')",nativeQuery = true)
    void insertBySQL(Date date , String testId, String userName);

    @Query(value = "select new com.utkarsh.leanassignment.dto.TestResultDTO(tr.date,tr.totalMarks,tr.options,tr.questions,tr.timeTaken) from TestResult tr where tr.testData.testId = :testId and tr.user.username= :userName")
    List<TestResultDTO> getBySQL(@Param("testId")String testId, @Param("userName") String userName);

    public List<TestResult> findByUser_Username(String Username, Pageable pageable);

    public List<TestResult> findByUser_Username(String Username);

}
