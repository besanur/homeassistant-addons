package com.besanur.prayertimes.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

  private URL baseUrl;
}
