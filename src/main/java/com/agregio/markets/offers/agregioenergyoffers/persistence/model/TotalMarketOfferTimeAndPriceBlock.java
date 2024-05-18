package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

@Builder
public record TotalMarketOfferTimeAndPriceBlock(HourRange hourRange, double floorPrice, double capacity) {
}
