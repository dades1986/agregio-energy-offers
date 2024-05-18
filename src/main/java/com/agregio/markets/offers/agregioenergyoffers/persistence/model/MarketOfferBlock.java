package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

import java.util.List;

@Builder
public record MarketOfferBlock(HourRange hourRange, double floorPrice, List<String> producerParkIds) {
}
