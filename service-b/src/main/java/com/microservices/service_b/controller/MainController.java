package com.microservices.service_b.controller;

import com.microservices.service_b.clientservice.Service_a_client;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    Service_a_client a_client;

    public MainController(Service_a_client a_client){
        this.a_client = a_client;
    }

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome To Service B", HttpStatus.OK);
    }

    @GetMapping("api/serviceb-data")
    public String serviceBData(){
        return "Data is returning from SERVICE B";
    }

    @GetMapping("/service-Adata")
    public ResponseEntity<String> serviceAdata(){
        return new ResponseEntity<>(a_client.getDataFromA(),HttpStatus.OK);
    }

}
