package com.iit.event.ticketing.system.configuration.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing)
 */
@Configuration
public class CorsConfiguration {

  private static final String PATH_PATTERN = "/api/**";
  private static final String ALLOWED_ORIGIN_FRONTEND_URL = "http://localhost:3000";
  private static final String HTTP_METHOD_GET = "GET";
  private static final String HTTP_METHOD_POST = "POST";

  /**
   * Create CORS configuration bean that sets up allowed origins and HTTP methods for specific endpoints
   *
   * @return WebMvcConfigurer object that configures CORS settings
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(final @NonNull CorsRegistry corsRegistry) {
        corsRegistry
            .addMapping(PATH_PATTERN)
            .allowedOrigins(ALLOWED_ORIGIN_FRONTEND_URL)
            .allowedMethods(HTTP_METHOD_GET, HTTP_METHOD_POST);
      }
    };
  }
}
