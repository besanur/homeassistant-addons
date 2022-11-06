package com.besanur.prayertimes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PrayerTimesApplication {

  public static void main(String[] args) {
    SpringApplication.run(PrayerTimesApplication.class, args);
  }

}
