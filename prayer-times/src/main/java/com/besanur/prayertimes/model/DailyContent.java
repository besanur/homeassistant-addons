package com.besanur.prayertimes.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DailyContent {
  
  public String verse;

  public String verseSource;

  public String hadith;

  public String hadithSource;

  public String pray;

  public String praySource;

}
