package com.agregio.markets.offers.agregioenergyoffers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Market offer creation payload")
public record CreateMarketOfferPayload(
        @Schema(description = "Market Type")
        @JsonProperty
        MarketTypePayload type,

        @Schema(description = "List of price blocks")
        @JsonProperty
        List<CreateMarketOfferPriceBlockPayload> priceBlocks
) {
}
