package com.besanur.prayertimes.ezanvakti;

import com.besanur.prayertimes.model.PrayerTime;
import com.besanur.prayertimes.model.PrayerTimeData;
import com.besanur.prayertimes.model.PrayerTimeRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EzanVaktiClient {

  private final String BASE_URL = "https://ezanvakti.herokuapp.com/vakitler/";
  private final RestTemplate restTemplate = new RestTemplate();

  public PrayerTimeData fetchPrayerTimes(int regionId) {
    log.info("Trying to fetch the prayer times from {}", BASE_URL);

    final ResponseEntity<PrayerTimeRequestEntity[]> response = restTemplate.getForEntity(
        "https://ezanvakti.herokuapp.com/vakitler/" + regionId, PrayerTimeRequestEntity[].class);

    if (!response.getStatusCode().is2xxSuccessful()) {
      log.warn("Something went wrong, status code {}", response.getStatusCode());
      throw new IllegalStateException("Status code not 2xx");
    }

    if (response.getBody() == null) {
      throw new IllegalStateException("No Body.");
    }

    final List<PrayerTime> prayerTimes = Arrays.stream(response.getBody()).map(requestEntity -> PrayerTime.builder()
        .date(requestEntity.getMiladiTarihKisa())
        .hijriDate(requestEntity.getHicriTarihUzun())
        .fajr(requestEntity.getImsak())
        .sun(requestEntity.getGunes())
        .dhuhur(requestEntity.getOgle())
        .asr(requestEntity.getIkindi())
        .maghrib(requestEntity.getAksam())
        .isha(requestEntity.getYatsi()).build()).collect(Collectors.toList());

    return PrayerTimeData.builder()
        .prayerTimes(prayerTimes)
        .regionId(regionId).build();
  }

}
