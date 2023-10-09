package com.besanur.prayertimes.diyanet;

import com.besanur.prayertimes.model.PrayerTime;
import com.besanur.prayertimes.model.PrayerTimeData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");
  private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ISO_LOCAL_TIME;

  public PrayerTimeData fetchMonthlyPrayerTimes(final int regionId) throws IOException {
    PrayerTimeData prayerTimeData = PrayerTimeData.builder().build();
    Elements prayerTimeRows = getAndParsePrayerTimeRows(regionId, prayerTimeData);

    List<PrayerTime> collect = prayerTimeRows.stream()
        .map(this::buildPrayerTimes)
        .collect(Collectors.toList());

    prayerTimeData.setPrayerTimes(collect);
    prayerTimeData.setRegionId(regionId);
    return prayerTimeData;
  }

  private PrayerTime buildPrayerTimes(final Element today) {
    return PrayerTime.builder()
        .date(LocalDate.parse(today.child(0).text(), DATE_FORMAT))
        .hijriDate(today.child(1).text())
        .fajr(LocalTime.parse(today.child(2).text(), TIME_FORMAT))
        .sun(LocalTime.parse(today.child(3).text(), TIME_FORMAT))
        .dhuhur(LocalTime.parse(today.child(4).text(), TIME_FORMAT))
        .asr(LocalTime.parse(today.child(5).text(), TIME_FORMAT))
        .maghrib(LocalTime.parse(today.child(6).text(), TIME_FORMAT))
        .isha(LocalTime.parse(today.child(7).text(), TIME_FORMAT))
        .build();
  }

  private Elements getAndParsePrayerTimeRows(final int regionId, PrayerTimeData prayerTimeData) throws IOException {
    String BASE_URL = "https://namazvakitleri.diyanet.gov.tr/en-US/";
    String url = BASE_URL + regionId;
    log.info("Fetching prayer times from {}", url);
    Document doc = Jsoup.connect(url).get();

    Elements elements = doc.getElementsByClass("vakit-table");
    Element monthlyTable = elements.stream()
        .filter(element -> element.getElementsByTag("caption").html().contains("Monthly")).findFirst().orElseThrow();

    String text = monthlyTable.getElementsByTag("caption").html();
    prayerTimeData.setRegion(text.substring(text.indexOf("for") + 4));

    Elements tbody = monthlyTable.getElementsByTag("tbody");
    Elements rowDays = tbody.get(0).getElementsByTag("tr");
    return rowDays;
  }
}
