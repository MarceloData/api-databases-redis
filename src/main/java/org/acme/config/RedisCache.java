package org.acme.config;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.redis.datasource.RedisDataSource;
import  io.quarkus.redis.datasource.value.SetArgs;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisCache {

    private final RedisDataSource redisDataSource;

    @Inject
    @ConfigProperty(name = "cache.default-ttl", defaultValue = "60")
    long defaultTtlInSeconds;

    public RedisCache(RedisDataSource redisDataSource) {
        this.redisDataSource = redisDataSource;
    }

    // Convert to Duration for use in cache methods
    private Duration getDefaultTtl() {
        return Duration.ofSeconds(defaultTtlInSeconds);
    }

   // Generic method to get an object from cache
    public <T> T get(String key, Class<T> clazz) {
        ValueCommands<String, T> commands = redisDataSource.value(clazz);
        return commands.get(key);
    }

    // Generic method to set an object in cache with TTL
    public <T> void set(String key, T value, Duration ttl) {
        @SuppressWarnings("unchecked")
        ValueCommands<String, T> commands = redisDataSource.value((Class<T>) value.getClass());
        commands.set(key, value, new SetArgs().ex(ttl));  // Set TTL for the cache entry
    }

    // Generic method to evict (delete) an object from cache
    public void evict(String key) {
        ValueCommands<String, String> commands = redisDataSource.value(String.class);
        commands.getdel(key);  // Delete the key from Redis
    }

    // Get from cache or compute and set if absent
    public <T> T getOrSetIfAbsent(String key, Supplier<T> computation, Duration ttl, Class<T> clazz) {
        T cached = get(key, clazz);
        if (cached != null) {
            return cached;
        } else {
            T result = computation.get();
            set(key, result, ttl);  // Cache the computed result with TTL
            return result;
        }
    }

    // Overloaded method to use the default TTL if no TTL is provided
    public <T> T getOrSetIfAbsent(String key, Supplier<T> computation, Class<T> clazz) {
        return getOrSetIfAbsent(key, computation, getDefaultTtl(), clazz);  // Use the default TTL if none is specified
    }

    // Get from cache or compute and set if absent
    public <T> List<T> getOrSetIfAbsent(String key, Supplier<List<T>> computation, Duration ttl) {
        @SuppressWarnings("unchecked")
        List<T> cached = get(key, List.class);
        if (cached != null) {
            return cached;
        } else {
            List<T> result = computation.get();
            set(key, result, ttl);  // Cache the computed result with TTL
            return result;
        }
    }

    // Overloaded method to use the default TTL if no TTL is provided
    public <T> List<T> getOrSetIfAbsent(String key, Supplier<List<T>> computation) {
        return getOrSetIfAbsent(key, computation, getDefaultTtl());  // Use the default TTL if none is specified
    }
}