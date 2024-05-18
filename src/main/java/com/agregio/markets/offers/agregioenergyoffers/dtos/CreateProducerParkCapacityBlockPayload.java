package com.agregio.markets.offers.agregioenergyoffers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateProducerParkCapacityBlockPayload(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        @Schema(description = "start datetime for the block", pattern = "HH:mm:ss")
        @JsonProperty HourRangePayload hour,

        @Schema(description = "energy production capacity: Mwh")
        @JsonProperty double capacity
) {
}
