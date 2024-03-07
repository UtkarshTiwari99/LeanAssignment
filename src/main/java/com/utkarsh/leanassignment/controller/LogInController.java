package com.utkarsh.leanassignment.controller;

import com.utkarsh.leanassignment.model.TestData;
import com.utkarsh.leanassignment.repository.TestDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LogInController{

    @Autowired
    TestDataRepository testDataRepository;

    @GetMapping("/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("login")
    public String login(){
        return "login";
    }

    /**
     * |3| Get Specific Question By TestId of Test and questionNumber
     **/
    @RequestMapping(value = "/quiz/{testId}/questions/{questionNumber}",method = RequestMethod.GET)
    public String getQuestion(Model model,
                              @PathVariable("questionNumber") Integer questionNumber, @PathVariable("testId") String testId
    ) {
        var data = testDataRepository.findQuestions(questionNumber, testId);
        if (data.size() != 1)
            return null;
        var testData=data.get(0);
        model.addAttribute("testData",testData);
        return "quiz";
    }

}