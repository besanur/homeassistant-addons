package com.besanur.prayertimes.cache;

import com.besanur.prayertimes.model.PrayerTimeData;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryCache {
  private Map<Integer, PrayerTimeData> cache;

  public InMemoryCache() {
    cache = new HashMap<>();
  }

  public void put(int key, PrayerTimeData value) {
    cache.put(key, value);
  }

  public PrayerTimeData get(int key) {
    return cache.get(key);
  }

  public void remove(int key) {
    cache.remove(key);
  }

  public void clear() {
    cache.clear();
  }

  public Collection<PrayerTimeData> getAll() {
    return cache.values();
  }
}
