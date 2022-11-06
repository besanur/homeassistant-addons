package com.besanur.prayertimes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PrayerTime {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private long id;

  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @JsonFormat(pattern = "dd.MM.yyyy")
  private LocalDate date;

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
