package com.agregio.markets.offers.agregioenergyoffers.persistence.repositories;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketOfferRepositoryImpl implements MarketOfferRepository {

    private final List<MarketOffer> offers = new ArrayList<>();

    @Override
    public void saveMarketOffer(MarketOffer marketOffer) {
        offers.add(marketOffer);
    }

    @Override
    public Optional<MarketOffer> findMarketByType(MarketType type) {
        return offers.stream().filter(offer -> offer.marketType() == type).findFirst();
    }

    @Override
    public List<MarketOffer> findAllMarketOffers() {

        return offers;
    }
}
