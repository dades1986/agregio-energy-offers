package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerPark;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkCapacityBlock;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.ProducerParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CreateProducerParkService {

    private final ProducerParkRepository producerParkRepository;


    public void createProducerPark(String name, ProducerParkType type, List<ProducerParkCapacityBlock> capacities) {
        ProducerPark producerPark = ProducerPark
                .builder()
                .id(generateProducerParkId(type.name(), capacities.size()))
                .name(name)
                .type(type)
                .capacity(capacities)
                .build();
        producerParkRepository.saveProducerPark(producerPark);
    }

    private String generateProducerParkId(String parcType, int parcCapacity) {
        long timestamp = System.currentTimeMillis();
        int hash = (parcType + parcCapacity).hashCode();
        return "P" + timestamp + "-" + hash;
    }
}
