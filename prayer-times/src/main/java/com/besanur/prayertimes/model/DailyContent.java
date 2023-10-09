package com.besanur.prayertimes.model;

import lombok.Builder;


@Builder
public record DailyContent(
    String verse,
    String verseSource,
    String hadith,
    String hadithSource,
    String pray,
    String praySource) {}
