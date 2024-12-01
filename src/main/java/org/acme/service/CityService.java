package org.acme.service;

import java.util.List;
import java.util.function.Supplier;

import org.acme.config.RedisCache;
import org.acme.models.pg.City;
import org.acme.repository.CityRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CityService {

    @Inject
    RedisCache redisCache;

    @Inject
    CityRepository cityRepository;

    public List<City> findTenCities(String identifier){
        String cacheKey = "tenCities:" + identifier;  // Cache key format

        // Supplier for computing the list of rankings if not in the cache
        Supplier<List<City>> result = () -> cityRepository.findFirstTen();

        return redisCache.getOrSetIfAbsent(cacheKey, result);
    }
}
