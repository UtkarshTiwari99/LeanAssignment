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

}