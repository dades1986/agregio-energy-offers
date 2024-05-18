package com.agregio.markets.offers.agregioenergyoffers.persistence.repositories;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerPark;

import java.util.List;
import java.util.Optional;

public interface ProducerParkRepository {

    void saveProducerPark(ProducerPark producerPark);

    Optional<ProducerPark> findParkById(String id);

    List<ProducerPark> findAllProducerParks();
}
