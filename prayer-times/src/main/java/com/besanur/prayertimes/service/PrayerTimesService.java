package com.besanur.prayertimes.service;

import com.besanur.prayertimes.exception.PrayerTimesParseException;
import com.besanur.prayertimes.model.PrayerTime;
import com.besanur.prayertimes.model.PrayerTimeData;
import com.besanur.prayertimes.repository.PrayerTimesDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PrayerTimesService {

  @Autowired
  private DiyanetPrayerTimesParser diyanetPrayerTimesParser;

  @Autowired
  private PrayerTimesDataRepository prayerTimesDataRepository;

  public PrayerTimeData getMonthlyPrayerTime(int regionId) {
    final Optional<PrayerTimeData> prayerTime = prayerTimesDataRepository.findById(regionId);

    if (prayerTime.isPresent()) {
      log.info("Found stored prayer times for regionId {}", regionId);
      return prayerTime.get();
    }

    return requestPrayerTimes(regionId);
  }

  public PrayerTimeData getDailyPrayerTime(int regionId) {
    final Optional<PrayerTimeData> prayerTime = prayerTimesDataRepository.findById(regionId);

    if (prayerTime.isPresent()) {
      log.info("Found stored prayer times for regionId {}", regionId);
      return buildDailyPrayerTimeFromMonthly(prayerTime.get());
    }
    return buildDailyPrayerTimeFromMonthly(requestPrayerTimes(regionId));
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void clean() {
    log.info("Schedule started for cleanup");

    final List<PrayerTimeData> updatedPrayerTimeData = new ArrayList<>();

    prayerTimesDataRepository.findAll().forEach(prayerTimeData -> {
      final List<PrayerTime> prayerTimeToRemove = new ArrayList<>();
      prayerTimeData.getPrayerTimes().forEach(prayerTime -> {
        if (LocalDate.now().isAfter(prayerTime.getDate())) {
          prayerTimeToRemove.add(prayerTime);
          log.debug("Removing old prayer time {}", prayerTime);
        }
      });
      prayerTimeData.getPrayerTimes().removeAll(prayerTimeToRemove);
      updatedPrayerTimeData.add(prayerTimeData);
    });
    updatedPrayerTimeData.forEach(prayerTimeData -> prayerTimesDataRepository.save(prayerTimeData));

    // fetch new prayer times if minimum of 10 is reached
    updatedPrayerTimeData.forEach(prayerTimeData -> {
      if (prayerTimeData.getPrayerTimes().size() <= 10) {
        requestPrayerTimes(prayerTimeData.getRegionId());
      }
    });
  }

  private PrayerTimeData buildDailyPrayerTimeFromMonthly(PrayerTimeData prayerTimeData) {
    return PrayerTimeData.builder()
        .prayerTimes(List.of(prayerTimeData.getPrayerTimes().get(0), prayerTimeData.getPrayerTimes().get(1)))
        .region(prayerTimeData.getRegion())
        .regionId(prayerTimeData.getRegionId())
        .build();
  }

  private PrayerTimeData requestPrayerTimes(int regionId) {
    try {
      log.info("Requesting prayer time for regionId {}", regionId);
      final PrayerTimeData monthlyPrayerTimes = diyanetPrayerTimesParser.fetchMonthlyPrayerTimes(regionId);
      prayerTimesDataRepository.save(monthlyPrayerTimes);
      return monthlyPrayerTimes;
    } catch (IOException e) {
      final var errMsg = "Can't parse prayer times from server";
      log.error(errMsg);
      throw new PrayerTimesParseException(errMsg);
    }
  }
}
