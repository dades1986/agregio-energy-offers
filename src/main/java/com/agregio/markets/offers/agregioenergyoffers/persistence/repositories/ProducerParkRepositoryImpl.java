package com.agregio.markets.offers.agregioenergyoffers.persistence.repositories;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerPark;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ProducerParkRepositoryImpl implements ProducerParkRepository {
    private final List<ProducerPark> parks = new ArrayList<>();

    @Override
    public void saveProducerPark(ProducerPark producerPark) {
        parks.add(producerPark);
    }

    @Override
    public Optional<ProducerPark> findParkById(String id) {
        return parks.stream().filter(p -> p.id().equals(id)).findFirst();
    }

    @Override
    public List<ProducerPark> findAllProducerParks() {
        return parks;
    }
}
