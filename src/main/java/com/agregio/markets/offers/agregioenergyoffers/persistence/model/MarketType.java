package com.agregio.markets.offers.agregioenergyoffers.persistence.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MarketType {
    PRIMARY("Primary Reserve Market"),
    SECONDARY("Secondary Reserve Market"),
    FAST("Fast  Reserve Market");

    private final String description;
}
