# Islamic Prayer Times fetched from [🇹🇷 Diyanet İşleri Başkanlığı](https://namazvakitleri.diyanet.gov.tr)

## About
I have created this addon to fetch the prayer times from the above site and show on an ESP-Home configured OLED Display.
This addon [(Docker Containers)](https://hub.docker.com/u/besanur) can also be used without Home Assistant. 

## REST API

1. Open https://namazvakitleri.diyanet.gov.tr and configure your location
2. Copy the region id which is displayed in the address bar (the number after ../tr-TR/)
    - https://namazvakitleri.diyanet.gov.tr/tr-TR/9541/istanbul-icin-namaz-vakti 

## Get daily prayer times

### Request

`GET /dailyPrayerTimes?{regionId}=REGION_ID`

    curl -i -H 'Accept: application/json' http://localhost:1453/dailyPrayerTimes?regionId=9541

### Response
```json
{
    "region": "Istanbul",
    "prayerTimes": [
        {
            "day": "22.05.2022",
            "fajr": "03:43",
            "sun": "05:34",
            "dhuhur": "13:06",
            "asr": "17:03",
            "maghrib": "20:28",
            "isha": "22:10"
        }
    ]
}
```

## Get monthly prayer times

### Request

`GET /monthlyPrayerTimes?{regionId}=REGION_ID`

    curl -i -H 'Accept: application/json' http://localhost:1453/dailyPrayerTimes?regionId=9541

### Response
```json
{
    "region": "Istanbul",
    "prayerTimes": [
        {
            "day": "22.05.2022",
            "fajr": "03:43",
            "sun": "05:34",
            "dhuhur": "13:06",
            "asr": "17:03",
            "maghrib": "20:28",
            "isha": "22:10"
        }
    ]
}
```


## Home Assistant Sensor Configuration
```yaml
# Namaz vakitleri
- platform: rest
  name: namaz_vakti
  resource: "http://localhost:1453/dailyPrayerTimes?regionId=<your_region_id>"
  scan_interval: 25200
  json_attributes_path: $.prayerTimes[0]
  json_attributes:
    - day
    - fajr
    - sun
    - dhuhur
    - asr
    - maghrib
    - isha
  value_template: >
    {% set now = now() %}

    {% set imsak = today_at(state_attr('sensor.namaz_vakti', 'fajr')) %}
    {% set gunes = today_at(state_attr('sensor.namaz_vakti', 'sun')) %}
    {% set oglen = today_at(state_attr('sensor.namaz_vakti', 'dhuhur')) %}
    {% set ikindi = today_at(state_attr('sensor.namaz_vakti', 'asr')) %}
    {% set aksam = today_at(state_attr('sensor.namaz_vakti', 'maghrib')) %}
    {% set yatsi = today_at(state_attr('sensor.namaz_vakti', 'isha')) %}

    {% if now < imsak %}
      Imsak: {{ state_attr('sensor.namaz_vakti', 'fajr') }} 
    {% elif now < gunes %}
      Güneş: {{ state_attr('sensor.namaz_vakti', 'sun') }} 
    {% elif now < oglen %}
      Öğlen: {{ state_attr('sensor.namaz_vakti', 'dhuhur') }} 
    {% elif now < ikindi %}
      Ikindi: {{ state_attr('sensor.namaz_vakti', 'asr') }} 
    {% elif now < aksam %}
      Akşam: {{ state_attr('sensor.namaz_vakti', 'maghrib') }} 
    {% elif now < yatsi %}
      Yatsı: {{ state_attr('sensor.namaz_vakti', 'isha') }} 
    {% else %}
      Imsak: {{ state_attr('sensor.namaz_vakti', 'fajr') }} 
    {% endif %}
```
