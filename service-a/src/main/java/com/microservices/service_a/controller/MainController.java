package com.microservices.service_a.controller;

import com.microservices.service_a.clientservice.Service_b_client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    Service_b_client b_client;

    public MainController(Service_b_client b_client){
        this.b_client = b_client;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome To Service A",HttpStatus.OK);
    }

    @GetMapping("/service-Bdata")
    public ResponseEntity<String> serviceBdata(){
        return new ResponseEntity<>(b_client.getDataFromB(),HttpStatus.OK);
    }


    @GetMapping("api/servicea-data")
    public String serviceAData(){
        return "Data is returning from SERVICE A";
    }

}
