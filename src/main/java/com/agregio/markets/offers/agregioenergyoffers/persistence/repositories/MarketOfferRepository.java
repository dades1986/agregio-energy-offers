package com.agregio.markets.offers.agregioenergyoffers.persistence.repositories;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;

import java.util.List;
import java.util.Optional;

public interface MarketOfferRepository {

    void saveMarketOffer(MarketOffer marketOffer);

    Optional<MarketOffer> findMarketByType(MarketType type);

    List<MarketOffer> findAllMarketOffers();
}
