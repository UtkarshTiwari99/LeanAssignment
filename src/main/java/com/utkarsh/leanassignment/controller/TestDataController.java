package com.utkarsh.leanassignment.controller;

import com.utkarsh.leanassignment.dto.TestResultDTO;
import com.utkarsh.leanassignment.dto.TestResultPostRequest;
import com.utkarsh.leanassignment.dto.TestResultPutRequest;
import com.utkarsh.leanassignment.model.TestData;
import com.utkarsh.leanassignment.model.TestResult;
import com.utkarsh.leanassignment.repository.TestDataRepository;
import com.utkarsh.leanassignment.repository.TestResultRepository;
import com.utkarsh.leanassignment.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class TestDataController {

    @Autowired
    public TestDataRepository testDataRepository;

    @Autowired
    public TestResultRepository testResultRepository;

    /**
     * |1| Upload Test Data URI
     **/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        try {
            if (ExcelUtil.hasExcelFormat(file)) {
                try {
                    var testData = ExcelUtil.excelToTestData(file.getInputStream());
                    testDataRepository.save(testData);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
                }
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * |3| Get Specific Question By TestId of Test and questionNumber
     **/
    @RequestMapping(value = "/tests/{testId}/questions/{questionNumber}",method = RequestMethod.GET)
    public TestData getQuestion(
            @PathVariable("questionNumber") Integer questionNumber, @PathVariable("testId") String testId
    ) {
        var data = testDataRepository.findQuestions(questionNumber, testId);
        if (data.size() != 1)
            return null;
        return data.get(0);
    }

    /**
     * |6| Get Result by Test TestId
     **/
    @GetMapping("/testResult/{testId}")
    public List<TestResultDTO> getTestResult(@RequestParam(required = false) String time, Authentication authentication, @PathVariable("testId") String testId) {
        return testResultRepository.getBySQL(testId, authentication.getName());
    }


    /**
     * |7| Get All Result For Users Using Pagination
     **/
    @GetMapping("/testResults")
    public List<TestResult> getAllTestResult(Authentication authentication, @RequestParam(required = false) Integer page) {
        Pageable pageable;
        System.out.println(page);
        if (page != null && page > 0) {
            pageable = PageRequest.of(page - 1, 2, Sort.by("date"));
            return testResultRepository.findByUser_Username(authentication.getName(), pageable);
        }
        return testResultRepository.findByUser_Username(authentication.getName());
    }


    @PostMapping("/testResults/")
    public ResponseEntity<?> createTestResult(Authentication authentication, @RequestBody TestResultPostRequest testResultPostRequest) {
        try {
            testResultRepository.insertBySQL(testResultPostRequest.getDate(), testResultPostRequest.getTestId(), authentication.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * |5| Store User Test Result
     **/
    @PutMapping("/testResults/")
    public ResponseEntity<?> updateTestResult(Authentication authentication, @RequestBody TestResultPutRequest testResultPutRequest) {
        try {
            testResultRepository.updateBySQL(testResultPutRequest.getAppend_questions(), testResultPutRequest.getAppend_options(), testResultPutRequest.getTestId(), authentication.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}