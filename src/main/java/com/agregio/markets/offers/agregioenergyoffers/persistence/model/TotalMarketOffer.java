package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.Builder;

import java.util.List;


@Builder
public record TotalMarketOffer(MarketType type, List<TotalMarketOfferTimeAndPriceBlock> priceBlocks) {
}
