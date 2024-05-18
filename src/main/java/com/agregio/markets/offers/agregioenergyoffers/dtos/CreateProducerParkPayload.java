package com.agregio.markets.offers.agregioenergyoffers.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateProducerParkPayload(
        @Schema(description = "park name")
        @JsonProperty String name,

        @Schema(description = "type of the park")
        @JsonProperty ProducerParkTypePayload type,

        @Schema(description = "time block capacity production")
        @JsonProperty List<CreateProducerParkCapacityBlockPayload> capacities
) {
}
