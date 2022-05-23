package com.besanur.prayertimes.rest;

import com.besanur.prayertimes.model.PrayerTimeData;
import com.besanur.prayertimes.service.DiyanetPrayerTimesParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class PrayerTimesController {

  @Autowired
  private DiyanetPrayerTimesParser diyanetPrayerTimesParser;

  @GetMapping("/monthlyPrayerTimes")
  public ResponseEntity<PrayerTimeData> getMonthlyPrayerTimes(@RequestParam int regionId) throws IOException {
    return ResponseEntity.ok(diyanetPrayerTimesParser.getMonthlyPrayerTimes(regionId));
  }
  
  @GetMapping("/dailyPrayerTimes")
  public ResponseEntity<PrayerTimeData> getDailyPrayerTimes(@RequestParam int regionId) throws IOException {
    return ResponseEntity.ok(diyanetPrayerTimesParser.getDailyPrayerTimes(regionId));
  }
}
