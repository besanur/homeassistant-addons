package com.besanur.prayertimes.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class PrayerTimeRequestEntity {

  @JsonProperty("KibleSaati")
  private String kibleSaati;
  @DateTimeFormat(pattern = "dd.MM.yyyy")
  @JsonFormat(pattern = "dd.MM.yyyy")
  @JsonProperty("MiladiTarihKisa")
  private LocalDate miladiTarihKisa;
  @JsonProperty("MiladiTarihKisaIso8601")
  private String miladiTarihKisaIso8601;
  @JsonProperty("MiladiTarihUzun")
  private String miladiTarihUzun;
  @JsonProperty("MiladiTarihUzunIso8601")
  private String miladiTarihUzunIso8601;
  @JsonProperty("HicriTarihKisa")
  private String hicriTarihKisa;
  @JsonProperty("HicriTarihUzun")
  private String hicriTarihUzun;
  @JsonProperty("AyinSekliURL")
  private String ayinSekliURL;
  @JsonProperty("GreenwichOrtalamaZamani")
  private int greenwichOrtalamaZamani;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Imsak")
  private LocalTime imsak;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Gunes")
  private LocalTime  gunes;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("GunesBatis")
  private LocalTime  gunesBatis;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("GunesDogus")
  private LocalTime  gunesDogus;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Ogle")
  private LocalTime  ogle;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Ikindi")
  private LocalTime  ikindi;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Aksam")
  private LocalTime  aksam;
  @JsonFormat(pattern = "HH:mm")
  @JsonProperty("Yatsi")
  private LocalTime  yatsi;
}
