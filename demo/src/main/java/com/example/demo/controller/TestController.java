package com.example.demo.controller;

import com.global.kinto.id.cognito.controller.model.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth endpoints
 */
@RestController
public class TestController {


    @PostMapping("/")
    public Object test() {

        return null;
    }

}
