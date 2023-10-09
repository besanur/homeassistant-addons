package com.besanur.prayertimes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrayerTime {

  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate date;

  private String hijriDate;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime fajr;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime sun;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime dhuhur;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime asr;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime maghrib;

  @JsonFormat(pattern = "HH:mm")
  private LocalTime isha;
}
