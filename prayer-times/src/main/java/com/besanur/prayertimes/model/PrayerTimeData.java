package com.besanur.prayertimes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrayerTimeData {

  private int regionId;

  private String region;

  private List<PrayerTime> prayerTimes;
}
