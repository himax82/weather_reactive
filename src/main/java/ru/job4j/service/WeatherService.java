package ru.job4j.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.job4j.model.Weather;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 35));
        weathers.put(5, new Weather(5, "Kiev", 45));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Mono<Weather> findHottest() {
        return Mono.justOrEmpty(weathers.values().stream()
                .max(Comparator.comparing(Weather::getTemperature)).get());
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Flux<Weather> allTemp(Integer id) {
        return Flux.fromIterable(weathers.values().stream()
                .filter(v -> v.getTemperature() > id)
                .collect(Collectors.toList()));
    }
}
