//package com.microservices.api_gateway.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//@Configuration
//public class GatewayCorsConfig {
//
//    @Bean
//    public CorsWebFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Allow all origins (in production, specify your frontend domains)
//        config.addAllowedOrigin("*");
//
//        // Allow all headers
//        config.addAllowedHeader("*");
//
//        // Allow all HTTP methods
//        config.addAllowedMethod("*");
//
//        // Allow credentials if needed
//        config.setAllowCredentials(true);
//
//        // Set max age
//        config.setMaxAge(3600L);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsWebFilter(source);
//    }
//}
