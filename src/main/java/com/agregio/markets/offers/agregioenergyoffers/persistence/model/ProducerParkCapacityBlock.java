package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

@Builder
public record ProducerParkCapacityBlock(HourRange hourRange, double capacity) {
}
