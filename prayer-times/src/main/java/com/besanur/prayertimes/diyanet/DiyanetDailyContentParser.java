package com.besanur.prayertimes.diyanet;

import com.besanur.prayertimes.model.DailyContent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class DiyanetDailyContentParser {

  private final static String URL = "https://diyanet.gov.tr";

  public DailyContent parseDailyContent() throws IOException {
    log.info("Requesting daily content from {}", URL);
    final Document doc = Jsoup.connect(URL).get();

    final String verse = doc.getElementsByClass("ayet").stream()
        .map(element -> element.getElementsByClass("ahd-content-text").html())
        .findFirst()
        .orElseThrow();

    final String verseSource = doc.getElementsByClass("ayet").next().stream()
        .map(element -> element.getElementsByClass("alt-sure-title").html())
        .findFirst()
        .orElseThrow();

    final String hadith = doc.getElementsByClass("hadis").stream()
        .map(element -> element.getElementsByClass("ahd-content-text").html())
        .findFirst()
        .orElseThrow();

    final String hadithSource = doc.getElementsByClass("hadis").next().stream()
        .map(element -> element.getElementsByClass("alt-sure-title").html())
        .findFirst()
        .orElseThrow();

    final String pray = doc.getElementsByClass("dua").stream()
        .map(element -> element.getElementsByClass("ahd-content-text").html())
        .findFirst()
        .orElseThrow();

    final String praySource = doc.getElementsByClass("dua").stream()
        .map(element -> element.getElementsByClass("alt-sure-title").html())
        .findFirst()
        .orElseThrow();

    return DailyContent.builder()
        .verse(verse)
        .verseSource(verseSource)
        .hadith(hadith)
        .hadithSource(hadithSource)
        .pray(pray)
        .praySource(praySource)
        .build();
  }
}
