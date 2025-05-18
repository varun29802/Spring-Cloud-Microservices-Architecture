Certainly! You’ve already secured your microservice (`resources`) using **Keycloak** with Spring Security and now want to **apply OAuth2/JWT-based authentication in your API Gateway** as well. Here's a step-by-step guide to help you secure your Spring Cloud Gateway using Keycloak.

---

## ✅ Goal

Secure the API Gateway so it authenticates and validates JWT tokens from Keycloak, and **only then forwards requests** to backend microservices like `service-a`, `service-b`, etc.

---

## 🔧 1. Add Required Dependencies to API Gateway

In your `api-gateway` project’s `pom.xml`, add:

```xml
<!-- For OAuth2 Resource Server support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

<!-- Spring Security for Gateway -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

---

## 🛡️ 2. Configure Security in `application.yml`

Add Keycloak's **issuer URI** and **JWK Set URI**:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/Micro-Service
          jwk-set-uri: http://localhost:8080/realms/Micro-Service/protocol/openid-connect/certs
```

---

## 🔐 3. Create a Security Configuration Class

Create a security configuration for the Gateway to:

* Validate JWT tokens
* Restrict access to certain routes

```java
package com.microservices.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(exchanges -> exchanges
                .pathMatchers("/service-a/**", "/service-b/**").authenticated()
                .anyExchange().permitAll()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}
```

> ✅ Note: Since you're using **Spring Cloud Gateway**, which is reactive, use `@EnableWebFluxSecurity` and `SecurityWebFilterChain`.

---

## 🚀 4. Test the Gateway

1. Get a **JWT access token** from Keycloak for a test user.
2. Call the secured API through the Gateway:

```bash
curl -H "Authorization: Bearer <token>" http://localhost:8080/service-a/api/v1/resource
```

You should only get a response **if the token is valid** and user has required permissions.

---

## ✅ Optional: Add Role-based Authorization

If you want to restrict access based on roles from Keycloak, extract them from the JWT and enforce them like:

```java
.authorizeExchange(exchanges -> exchanges
    .pathMatchers("/service-a/**").hasAuthority("ROLE_user")
    .pathMatchers("/service-b/**").hasAuthority("ROLE_admin")
    .anyExchange().permitAll()
)
```

Make sure the roles are available as `authorities` in the JWT. You might need to adjust Keycloak mappers for this.

---

## 🧪 Summary

* ✅ Add Spring Security + OAuth2 dependencies to Gateway
* ✅ Configure JWT issuer and JWK URI
* ✅ Secure routes in Gateway using `SecurityWebFilterChain`
* ✅ Test with real tokens from Keycloak

Let me know if you’d like help customizing JWT claim mapping (e.g., role extraction) or setting up Keycloak clients and realms.
