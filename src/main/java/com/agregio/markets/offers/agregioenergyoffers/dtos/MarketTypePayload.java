package com.agregio.markets.offers.agregioenergyoffers.dtos;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MarketTypePayload {
    PRIMARY("Primary Reserve Market"),
    SECONDARY("Secondary Reserve Market"),
    FAST("Fast Reserve Market");

    private final String description;
}

