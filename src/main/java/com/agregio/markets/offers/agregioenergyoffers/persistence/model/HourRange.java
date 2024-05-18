package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalTime;

@Builder
public record HourRange(
        @JsonFormat(pattern = "HH:mm:ss") LocalTime from,
        @JsonFormat(pattern = "HH:mm:ss") LocalTime to) {
}
