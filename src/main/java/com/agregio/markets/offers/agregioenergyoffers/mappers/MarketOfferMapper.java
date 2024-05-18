package com.agregio.markets.offers.agregioenergyoffers.mappers;

import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateMarketOfferPriceBlockPayload;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.HourRange;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOfferBlock;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MarketOfferMapper {

    public static List<MarketOfferBlock> mapPayloadListToModelList(List<CreateMarketOfferPriceBlockPayload> payloadList) {
        return payloadList.stream()
                .map(MarketOfferMapper::mapPayloadToModel)
                .collect(Collectors.toList());
    }

    public static MarketOfferBlock mapPayloadToModel(CreateMarketOfferPriceBlockPayload createMarketOfferPriceBlockPayload) {
        return MarketOfferBlock
                .builder()
                .hourRange(HourRange
                        .builder()
                        .from(createMarketOfferPriceBlockPayload.hour().from())
                        .to(createMarketOfferPriceBlockPayload.hour().to())
                        .build())
                .floorPrice(createMarketOfferPriceBlockPayload.floorPrice())
                .producerParkIds(createMarketOfferPriceBlockPayload.producerParkIds())
                .build();
    }
}
