package com.agregio.markets.offers.agregioenergyoffers.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record HourRangePayload(
        @JsonFormat(pattern = "HH:mm:ss") LocalTime from,
        @JsonFormat(pattern = "HH:mm:ss") LocalTime to
) {
}
