package fr.dauphine.AuditFinance.config;



import org.springframework.context.annotation.Bean;

import org.springframework.stereotype.Component;

import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Cors filter allowing cross-domain requests
 * @author sarah
 *
 */
@Component
public class CorsConfiguration {

   private final ConfigurationService configurationService;

    public CorsConfiguration(ConfigurationService configurationService) {
      this.configurationService = configurationService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();

        configuration.setAllowedOrigins(new ArrayList<>(configurationService.getCorsAllowedOrigins()));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("Cache-Control","Content-Language","Content-Type","Expires","Last-Modified","Pragma","Location"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
