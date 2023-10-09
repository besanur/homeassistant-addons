package com.besanur.prayertimes.service;

import com.besanur.prayertimes.cache.InMemoryCache;
import com.besanur.prayertimes.config.AppProperties;
import com.besanur.prayertimes.diyanet.DiyanetPrayerTimesParser;
import com.besanur.prayertimes.exception.PrayerTimesParseException;
import com.besanur.prayertimes.ezanvakti.EzanVaktiClient;
import com.besanur.prayertimes.model.PrayerTime;
import com.besanur.prayertimes.model.PrayerTimeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class PrayerTimesService {

  @Autowired
  private DiyanetPrayerTimesParser diyanetPrayerTimesParser;
  @Autowired
  private EzanVaktiClient ezanVaktiClient;
  @Autowired
  private AppProperties appProperties;
  @Autowired
  private InMemoryCache store;

  public PrayerTimeData getMonthlyPrayerTime(int regionId) {
    final PrayerTimeData prayerTime = store.get(regionId);

    if (prayerTime != null) {
      log.info("Found stored prayer times for regionId {}", regionId);
      return prayerTime;
    }
    return requestPrayerTimes(regionId);
  }

  public PrayerTimeData getDailyPrayerTime(int regionId) {
    final PrayerTimeData prayerTime = store.get(regionId);

    if (prayerTime != null) {
      log.info("Found stored prayer times for regionId {}", regionId);
      return buildDailyPrayerTimeFromMonthly(prayerTime);
    }
    return buildDailyPrayerTimeFromMonthly(requestPrayerTimes(regionId));
  }

  @Scheduled(cron = "0 0 0 * * ?")
  public void clean() {
    log.info("Schedule started for cleaning up the Prayer Times");

    store.getAll().forEach(prayerTimeData -> {
      final List<PrayerTime> prayerTimeToRemove = new ArrayList<>();
      prayerTimeData.getPrayerTimes().forEach(prayerTime -> {
        if (LocalDate.now().isAfter(prayerTime.getDate())) {
          prayerTimeToRemove.add(prayerTime);
          log.debug("Removing old prayer time {}", prayerTime.getDate());
        }
      });
      prayerTimeData.getPrayerTimes().removeAll(prayerTimeToRemove);
    });

    // fetch new prayer times if minimum of 10 is reached
    store.getAll().forEach(prayerTimeData -> {
      if (prayerTimeData.getPrayerTimes().size() <= 10) {
        requestPrayerTimes(prayerTimeData.getRegionId());
      }
    });
  }

  private PrayerTimeData requestPrayerTimes(int regionId) {
    log.info("Requesting prayer time for regionId {}", regionId);

    if (!appProperties.isUseEzanVaktiApiOnly()) {
      try {
        final PrayerTimeData prayerTimeData = diyanetPrayerTimesParser.fetchMonthlyPrayerTimes(regionId);
        store.put(prayerTimeData.getRegionId(), prayerTimeData);
        return prayerTimeData;
      } catch (IOException e) {
        final var errMsg = "Can't parse prayer times from 'diyanet.gov.tr', fallback to ezanvakti";
        log.error(errMsg, e);
      }
    }

    try {
      final PrayerTimeData prayerTimeData = ezanVaktiClient.fetchPrayerTimes(regionId);
      store.put(prayerTimeData.getRegionId(), prayerTimeData);
      return prayerTimeData;
    } catch (Exception e) {
      final var errMsg = "Can't parse prayer times from ezanvakti";
      log.error(errMsg, e);
      throw new PrayerTimesParseException(errMsg);
    }
  }

  private PrayerTimeData buildDailyPrayerTimeFromMonthly(PrayerTimeData prayerTimeData) {
    return PrayerTimeData.builder()
        .prayerTimes(List.of(prayerTimeData.getPrayerTimes().get(0), prayerTimeData.getPrayerTimes().get(1)))
        .region(prayerTimeData.getRegion()).regionId(prayerTimeData.getRegionId()).build();
  }
}
