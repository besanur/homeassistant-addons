package com.besanur.prayertimes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PrayerTimesParseException extends RuntimeException{

  public PrayerTimesParseException(final String message) {
    super(message);
  }
}
