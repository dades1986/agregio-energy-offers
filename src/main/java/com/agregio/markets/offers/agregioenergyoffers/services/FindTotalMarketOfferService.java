package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.*;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.MarketOfferRepository;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.ProducerParkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindTotalMarketOfferService {

    private final MarketOfferRepository marketOfferRepository;

    private final ProducerParkRepository producerParkRepository;

    public Optional<TotalMarketOffer> retrieve(MarketType type) {
        return marketOfferRepository.findMarketByType(type).map(marketOffer -> {
            List<TotalMarketOfferTimeAndPriceBlock> blocks = marketOffer.priceBlocks().stream().map(priceBlock -> {
                double capacity = getCapacityForAllProducerParkAtRange(priceBlock);
                return TotalMarketOfferTimeAndPriceBlock.builder().hourRange(priceBlock.hourRange()).capacity(capacity).floorPrice(priceBlock.floorPrice()).build();
            }).collect(Collectors.toList());

            return TotalMarketOffer.builder().type(type).priceBlocks(blocks).build();
        });
    }


    double getCapacityForAllProducerParkAtRange(MarketOfferBlock priceBlock) {
        return priceBlock.producerParkIds().stream()
                .mapToDouble(id -> getCapacityForProducerParkAtRange(id, priceBlock.hourRange()))
                .sum();
    }

    double getCapacityForProducerParkAtRange(String producerParkId, HourRange range) {
        Optional<ProducerPark> producerPark = producerParkRepository.findParkById(producerParkId);
        return producerPark.map(p -> p.getCapacityForRange(range)).orElse(0.0);
    }
}
