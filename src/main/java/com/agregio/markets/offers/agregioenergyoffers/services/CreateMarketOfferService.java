package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOfferBlock;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.MarketOfferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateMarketOfferService {
    private final MarketOfferRepository marketOfferRepository;

    public void createMarketOffer(MarketType type, List<MarketOfferBlock> priceBlocks) {
        MarketOffer marketOffer = MarketOffer
                .builder()
                .id(generateMarketOfferId(type.name(), priceBlocks.size()))
                .marketType(type)
                .priceBlocks(priceBlocks)
                .build();
        marketOfferRepository.saveMarketOffer(marketOffer);
    }

    private String generateMarketOfferId(String parcType, int parcCapacity) {
        long timestamp = System.currentTimeMillis();
        int hash = (parcType + parcCapacity).hashCode();
        return "M" + timestamp + "-" + hash;
    }
}
