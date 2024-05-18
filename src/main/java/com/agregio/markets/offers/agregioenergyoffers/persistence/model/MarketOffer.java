package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

import java.util.List;

@Builder
public record MarketOffer(String id, MarketType marketType, List<MarketOfferBlock> priceBlocks) {
}
