package org.acme.repository;

import java.util.List;

import org.acme.models.mysql.Orders;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrdersRepository implements PanacheRepository<Orders> {

    public List<Orders> findOrdersByCustomerNumber(Integer customerNumber){
        return list("customerNumber= ?1", customerNumber);
    }
}
