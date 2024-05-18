package com.agregio.markets.offers.agregioenergyoffers.dtos;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ProducerParkTypePayload {
    SOLAR_POWER("Solar power energy"),
    WIND_POWER("Wind power energy"),
    HYDRO_POWER("Hydro power energy");

    private final String description;
}