package com.utkarsh.leanassignment.controller;

import com.utkarsh.leanassignment.dto.TestResultDTO;
import com.utkarsh.leanassignment.dto.TestResultPostRequest;
import com.utkarsh.leanassignment.dto.TestResultPutRequest;
import com.utkarsh.leanassignment.model.Question;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
     * |2| Get Specific Question By TestId of Test and questionNumber
     **/
    @RequestMapping(value = "startTest/{testId}",method = RequestMethod.GET)
    public ResponseEntity<Question> startTest(@PathVariable("testId") String testId) {
        Question question;
        try{
            var data = testDataRepository.findQuestions(1, testId);
            if(data.size()!=1)
                throw new Exception("Bad Request");
            var testData=data.get(0);
            question = testData.getQuestions().stream().filter((q)->q.getQuestionNumber()==1).findFirst().get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(question);
    }

    /**
     * |3| Get Specific Question By TestId of Test and questionNumber
     **/
    @RequestMapping(value = "/tests/{testId}/questions/{questionNumber}",method = RequestMethod.GET)
    public ResponseEntity<Question> getQuestion(
            @PathVariable("questionNumber") Integer questionNumber, @PathVariable("testId") String testId
    ) {
        Question question;
        try{
            var data = testDataRepository.findQuestions(1, testId);
            if(data.size()!=1)
                throw new Exception("Bad Request");
            var testData=data.get(0);
            question = testData.getQuestions().stream().filter((q)->q.getQuestionNumber()==1).findFirst().get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(question);
    }

    /**
     * |6| Get Result by Test TestId
     **/
    @GetMapping("/testResult/{testId}")
    public ResponseEntity<List<TestResultDTO>> getTestResult(@RequestParam(required = false) String time, Authentication authentication, @PathVariable("testId") String testId) {
        List<TestResultDTO> list=new ArrayList<>();
        try {
            list=testResultRepository.getBySQL(testId, authentication.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(testResultRepository.getBySQL(testId, authentication.getName()));
    }


    /**
     * |7| Get All Result For Users Using Pagination
     **/
    @GetMapping("/testResults")
    public ResponseEntity<List<TestResult>> getAllTestResult(Authentication authentication, @RequestParam(required = false) Integer page) {
        Pageable pageable;
        List<TestResult> list=new ArrayList<TestResult>();
        try {
            if (page != null && page > 0) {
                pageable = PageRequest.of(page - 1, 2, Sort.by("date"));
                list= testResultRepository.findByUser_Username(authentication.getName(), pageable);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }


    @PostMapping("/result/")
    public ResponseEntity<?> createTestResult(Authentication authentication, @RequestBody TestResultPostRequest testResultPostRequest) {
        try {
            var localDate = LocalDate.parse(testResultPostRequest.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            var instant=localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            Date date = Date.from(instant);
            testResultRepository.insertBySQL(date, testResultPostRequest.getTestId(), authentication.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * |5| Store User Test Result
     **/
    @PutMapping("/testDataResult")
    public ResponseEntity<?> updateTestResult(Authentication authentication, @RequestBody TestResultPutRequest testResultPutRequest) {
        try {
            testResultRepository.updateBySQL(testResultPutRequest.getAppend_questions(), testResultPutRequest.getAppend_options(), testResultPutRequest.getTestId(), authentication.getName(),testResultPutRequest.getMarks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}