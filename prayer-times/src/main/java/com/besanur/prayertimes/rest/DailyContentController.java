package com.besanur.prayertimes.rest;

import com.besanur.prayertimes.diyanet.DiyanetDailyContentParser;
import com.besanur.prayertimes.model.DailyContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class DailyContentController {

  @Autowired
  private DiyanetDailyContentParser diyanetDailyContentParser;

  @GetMapping("/dailyContent")
  public DailyContent getDailyContent() throws IOException {
    final DailyContent dailyContent = diyanetDailyContentParser.parseDailyContent();
    log.info("Parsed daily content {}", dailyContent);
    return dailyContent;
  }
}
