package br.com.fiap3esph.autoescola3esph.config.web;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "api.cors")
public record PropriedadesDeCors(
        List<String> allowedOrigins,
        List<String> allowedMethods,
        List<String> allowedHeaders,
        List<String> exposedHeaders,
        Boolean allowCredentials,
        Long maxAge) {

    public PropriedadesDeCors {
        if (allowedOrigins == null || allowedOrigins.isEmpty()) {
            allowedOrigins = List.of(
                    "http://localhost:3000",
                    "http://127.0.0.1:3000",
                    "http://localhost:5500",
                    "http://127.0.0.1:5500",
                    "http://localhost:8085"
            );
        }
        if (allowedMethods == null || allowedMethods.isEmpty()) {
            allowedMethods = List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "HEAD");
        }
        if (allowedHeaders == null || allowedHeaders.isEmpty()) {
            allowedHeaders = List.of("Authorization", "Content-Type", "Accept", "Origin");
        }
        if (exposedHeaders == null) {
            exposedHeaders = List.of("Location");
        }
        if (allowCredentials == null) {
            allowCredentials = Boolean.TRUE;
        }
        if (maxAge == null) {
            maxAge = 3600L;
        }
    }
}
