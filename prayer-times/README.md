# 🕋 Islamic Prayer Times fetched from [Presidency of Religious Affairs](https://namazvakitleri.diyanet.gov.tr/)

[![Builder](https://github.com/besanur/homeassistant-addons/actions/workflows/builder.yml/badge.svg)](https://github.com/besanur/homeassistant-addons/actions/workflows/builder.yml)

- [Islamic Prayer Times fetched from Presidency of Religious Affairs](#-islamic-prayer-times-fetched-from-presidency-of-religious-affairs)
  - [About](#about)
  - [REST API](#rest-api)
    - [Get daily prayer times](#get-daily-prayer-times)
    - [Get monthly prayer times](#get-monthly-prayer-times)
  - [Home Assistant - Sensor Configuration](#home-assistant---sensor-configuration)
    - [Get prayer times over REST sensor](#get-prayer-times-over-rest-sensor)
  - [ESPHome - Example configuration](#esphome---example-configuration)
  - [Development](#development)
    - [Build addon locally in devcontainer](#build-addon-locally-in-devcontainer)

## About

I have created this addon to fetch the prayer times from the above site and show on an ESP-Home
configured OLED Display. Also it is possible to play adhan on TV when the prayer time has come.

This addon [(Docker Containers)](https://hub.docker.com/u/besanur) can also be used without Home Assistant. Just pull the docker image an call the REST endpoint to get the prayer times.

## REST API

1. Open https://namazvakitleri.diyanet.gov.tr and configure your location
2. Copy the region id which is displayed in the address bar, the number after `../tr-TR/` or `../en-EN/` depends on the language. For https://namazvakitleri.diyanet.gov.tr/tr-TR/9541/istanbul-icin-namaz-vakti the id would be ➡️ **9541**

### Get daily prayer times

**Request**

`GET /api/dailyPrayerTimes/{regionId}`

    curl -i -H 'Accept: application/json' http://localhost:1453/api/dailyPrayerTimes/9541

**Response**

Will return the prayer time of the current and next day

```json
{
  "regionId": 9541,
  "region": "Istanbul",
  "prayerTimes": [
    {
      "date": "06.11.2022",
      "fajr": "06:07",
      "sun": "07:33",
      "dhuhur": "12:53",
      "asr": "15:36",
      "maghrib": "18:02",
      "isha": "19:23"
    },
    {
      "date": "07.11.2022",
      "fajr": "06:08",
      "sun": "07:35",
      "dhuhur": "12:53",
      "asr": "15:35",
      "maghrib": "18:01",
      "isha": "19:22"
    }
  ]
}
```

### Get monthly prayer times

**Request**

`GET /api//monthlyPrayerTimes/{regionId}`

    curl -i -H 'Accept: application/json' http://localhost:1453/api/monthlyPrayerTimes/9541

**Response**

Will return the prayer time for a month

```json
{
    "regionId": 9541,
    "region": "Istanbul",
    "prayerTimes": [
        {
            "date": "06.11.2022",
            "fajr": "06:07",
            "sun": "07:33",
            "dhuhur": "12:53",
            "asr": "15:36",
            "maghrib": "18:02",
            "isha": "19:23"
        },
        {
         
        },
        {
            "date": "06.12.2022",
            "fajr": "06:36",
            "sun": "08:07",
            "dhuhur": "13:00",
            "asr": "15:22",
            "maghrib": "17:43",
            "isha": "19:09"
        }
    ]
}
```

### Get daily content (only in turkish)

**Request**

`GET /api/dailyContent`

    curl -i -H 'Accept: application/json' http://localhost:1453/api/dailyContent

**Response**

Will return the daily content. Verse, Hadith and Pray with sources

```json
{
  "verse": "Ey iman edenler! Allah ve resulünün önüne geçmeyin, Allah'a itaatsizlikten sakının! Şüphesiz Allah her şeyi işitmekte ve bilmektedir.",
  "verseSource": "(Hucurât, 49/1)",
  "hadith": "Allah'a sığınan kimseyi koruyup himaye ediniz. Allah için isteyene veriniz. Sizi davet edenin davetine icabet ediniz.",
  "hadithSource": "(Ebû Dâvud, \"Zekât\", 37)",
  "pray": "Ey Allah'ım! Güzellikleri Senden başkası veremez. Kötülükleri de Senden başkası önleyemez. Binaenaleyh (kötülüğü önlemek için gerekli olan) güç de (güzelliği elde etmek için gerekli olan) kuvvet de ancak Senindir.",
  "praySource": "(Ebû Dâvud, \"Tıb\", 24)"
}
```

## Home Assistant - Sensor Configuration

### Get prayer times over REST sensor

[Example sensor config](example/prayer-time-sensor.yaml)

[Example automation for athan on TV](example/automations.yaml)

## ESPHome - Example configuration

[Example ESPHome config ](example/esphome-display.yaml)

## Development

### Build addon locally in devcontainer

```bash
docker run \
  --rm \
  -it \
  --name builder \
  --privileged \
  -v "$(pwd)/prayer-times:/data" \
  -v /var/run/docker.sock:/var/run/docker.sock:ro \
  homeassistant/amd64-builder \
  -t /data \
  --amd64 \
  -i {arch}-addon-prayer-times \
  -d local
```

> see [Home Assistant builder](https://github.com/home-assistant/builder) for more options
