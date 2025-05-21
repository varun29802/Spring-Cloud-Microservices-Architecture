package com.microservices.service_a.clientservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "service-b")
public interface Service_b_client {

    @GetMapping("api/serviceb-data")
    String getDataFromB();
}
