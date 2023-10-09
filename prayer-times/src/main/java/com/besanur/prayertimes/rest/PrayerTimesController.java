package com.besanur.prayertimes.rest;

import com.besanur.prayertimes.model.PrayerTimeData;
import com.besanur.prayertimes.service.PrayerTimesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class PrayerTimesController {

  @Autowired
  private PrayerTimesService prayerTimesService;

  @GetMapping("/dailyPrayerTimes/{regionId}")
  public ResponseEntity<PrayerTimeData> getDailyPrayerTimes(@PathVariable int regionId) {
    log.info("Request for daily prayer times for region with id {}", regionId);
    final PrayerTimeData prayerTime = prayerTimesService.getDailyPrayerTime(regionId);
    return ResponseEntity.ok(prayerTime);
  }

  @GetMapping("/monthlyPrayerTimes/{regionId}")
  public ResponseEntity<PrayerTimeData> getMonthlyPrayerTimes(@PathVariable int regionId) {
    log.info("Request for monthly prayer times for region with id {}", regionId);
    final PrayerTimeData prayerTime = prayerTimesService.getMonthlyPrayerTime(regionId);
    return ResponseEntity.ok(prayerTime);
  }
}
