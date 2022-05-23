package com.besanur.prayertimes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PrayerTimeData {

  private String region;

  private List<PrayerTimes> prayerTimes;

}
