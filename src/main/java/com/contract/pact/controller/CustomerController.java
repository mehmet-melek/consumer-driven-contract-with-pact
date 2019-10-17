package com.contract.pact.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @RequestMapping("/path")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
