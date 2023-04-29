package com.besanur.prayertimes.rest;

import com.besanur.prayertimes.model.DailyContent;
import com.besanur.prayertimes.service.DailyContentParser;
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
  private DailyContentParser dailyContentParser;

  @GetMapping("/dailyContent")
  public DailyContent getDailyContent() throws IOException {
    return dailyContentParser.parseDailyContent();
  }
}
