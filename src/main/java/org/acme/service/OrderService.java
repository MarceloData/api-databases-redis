package org.acme.service;

import java.util.List;
import java.util.function.Supplier;

import org.acme.config.RedisCache;
import org.acme.models.mysql.Orders;
import org.acme.repository.OrdersRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {

    @Inject
    RedisCache redisCache;

    @Inject
    OrdersRepository ordersRepository;

    public List<Orders> findOrdersByClient(Integer customerNumber){
        String cacheKey = "orderByClient:" + customerNumber.toString();  // Cache key format

        // Supplier for computing the list of rankings if not in the cache
        Supplier<List<Orders>> result = () -> ordersRepository.findOrdersByCustomerNumber(customerNumber);

        return redisCache.getOrSetIfAbsent(cacheKey, result);
    }
}
