package com.besanur.prayertimes.service;

import com.besanur.prayertimes.config.AppProperties;
import com.besanur.prayertimes.model.PrayerTimeData;
import com.besanur.prayertimes.model.PrayerTimes;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DiyanetPrayerTimesParser {

  @Autowired
  private AppProperties appProperties;
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_TIME;

  public PrayerTimeData getMonthlyPrayerTimes(final int regionId) throws IOException {
    PrayerTimeData prayerTimeData = PrayerTimeData.builder().build();
    final Elements prayerTimeRows = getAndParsePrayerTimeRows(regionId, prayerTimeData);

    final List<PrayerTimes> collect = prayerTimeRows.stream()
        .map(this::buildPrayerTimes)
        .collect(Collectors.toList());

    prayerTimeData.setPrayerTimes(collect);
    return prayerTimeData;
  }

  public PrayerTimeData getDailyPrayerTimes(final int regionId) throws IOException {
    PrayerTimeData prayerTimeData = PrayerTimeData.builder().build();
    final Elements prayerTimeRows = getAndParsePrayerTimeRows(regionId, prayerTimeData);
    final Element today = prayerTimeRows.get(0);

    final PrayerTimes prayerTimes = buildPrayerTimes(today);

    prayerTimeData.setPrayerTimes(List.of(prayerTimes));

    return prayerTimeData;
  }

  private PrayerTimes buildPrayerTimes(final Element today) {
    return PrayerTimes.builder()
        .day(LocalDate.parse(today.child(0).text(), DATE_FORMAT))
        .fajr(LocalTime.parse(today.child(1).text(), TIME_FORMAT))
        .sun(LocalTime.parse(today.child(2).text(), TIME_FORMAT))
        .dhuhur(LocalTime.parse(today.child(3).text(), TIME_FORMAT))
        .asr(LocalTime.parse(today.child(4).text(), TIME_FORMAT))
        .maghrib(LocalTime.parse(today.child(5).text(), TIME_FORMAT))
        .isha(LocalTime.parse(today.child(6).text(), TIME_FORMAT))
        .build();
  }

  private Elements getAndParsePrayerTimeRows(final int regionId, PrayerTimeData prayerTimeData) throws IOException {
    final String url = appProperties.getBaseUrl().toString() + regionId;
    log.info("Requesting prayer times from {}", url);
    final Document doc = Jsoup.connect(url).get();

    final Elements elements = doc.getElementsByClass("vakit-table");
    final Element monthlyTable = elements.stream()
        .filter(element -> element.getElementsByTag("caption").html().contains("Monthly")).findFirst().orElseThrow();

    String text = monthlyTable.getElementsByTag("caption").html();
    prayerTimeData.setRegion(text.substring(text.indexOf("for") + 4));

    final Elements tbody = monthlyTable.getElementsByTag("tbody");
    final Elements rowDays = tbody.get(0).getElementsByTag("tr");
    return rowDays;
  }
}
