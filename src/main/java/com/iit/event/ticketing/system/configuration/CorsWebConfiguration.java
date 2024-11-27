package com.iit.event.ticketing.system.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for setting up CORS (Cross-Origin Resource Sharing)
 */
@Configuration
public class CorsWebConfiguration {

  private static final String ALLOWED_ORIGIN_FRONTEND_URL_LOCALHOST = "http://localhost:3000";

  /**
   * Create CORS configuration bean that sets up allowed origins and HTTP methods for specific endpoints
   *
   * @return WebMvcConfigurer object that configures CORS settings
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      /**
       * Add CORS mappings
       *
       * @param corsRegistry CorsRegistry (Not null)
       */
      @Override
      public void addCorsMappings(final @NonNull CorsRegistry corsRegistry) {
        corsRegistry
            .addMapping("/**")
            .allowedOrigins(ALLOWED_ORIGIN_FRONTEND_URL_LOCALHOST)
            .allowedOriginPatterns(CorsConfiguration.ALL)
            .allowedHeaders(CorsConfiguration.ALL)
            .allowCredentials(true)
            .allowedMethods("GET", "POST");
      }
    };
  }
}
