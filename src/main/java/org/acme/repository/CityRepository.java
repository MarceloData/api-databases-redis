package org.acme.repository;

import java.util.List;

import org.acme.models.pg.City;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CityRepository implements PanacheRepository<City> {

    public List<City> findFirstTen(){
        return findAll().page(0,10).list();
    }

}
