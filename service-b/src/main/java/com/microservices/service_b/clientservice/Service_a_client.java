package com.microservices.service_b.clientservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-a")
public interface Service_a_client {

    @GetMapping("api/servicea-data")
    String getDataFromA();
}
