package com.agregio.markets.offers.agregioenergyoffers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CreateMarketOfferPriceBlockPayload(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        @Schema(description = "Block time start time", pattern = "HH:mm:ss")
        @JsonProperty HourRangePayload hour,

        @Schema(description = "Threshold price")
        @JsonProperty double floorPrice,

        @Schema(description = "Producer park identifiers list")
        @JsonProperty List<String> producerParkIds
) {
}
