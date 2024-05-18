package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

import java.util.List;

@Builder
public record ProducerPark(String id, String name, ProducerParkType type, List<ProducerParkCapacityBlock> capacity) {

    public double getCapacityForRange(HourRange range) {
        return capacity.stream()
                .filter(c -> c.hourRange().equals(range))
                .mapToDouble(ProducerParkCapacityBlock::capacity)
                .findFirst()
                .orElse(0.0);
    }
}
