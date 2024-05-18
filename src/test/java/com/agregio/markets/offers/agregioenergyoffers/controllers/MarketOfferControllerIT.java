package com.agregio.markets.offers.agregioenergyoffers.controllers;

import com.agregio.markets.offers.agregioenergyoffers.AgregioEnergyOffersApplication;
import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateMarketOfferPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateMarketOfferPriceBlockPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.HourRangePayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.MarketTypePayload;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.MarketOfferRepository;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AgregioEnergyOffersApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@AutoConfigureMockMvc
class MarketOfferControllerIT {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    private MarketOfferRepository marketOfferRepository;

    @Test
    void should_create_market_offer_when_posting_a_new_market() throws Exception {
        HourRangePayload tata = HourRangePayload
                .builder()
                .from(LocalTime.of(11, 0))
                .to(LocalTime.of(14, 0))
                .build();
        CreateMarketOfferPayload params = new CreateMarketOfferPayload(MarketTypePayload.PRIMARY,
                List.of(new CreateMarketOfferPriceBlockPayload(tata, 321.0, List.of("producerParkId"))));

        String content = new ObjectMapper().registerModule(new JavaTimeModule())
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .writeValueAsString(params);

        mvc.perform(post("/api/market/offer/create").contentType("application/json").content(content)).andExpect(status().isCreated());

        List<MarketOffer> marketOffers = marketOfferRepository.findAllMarketOffers();
        assertThat(marketOffers).hasSize(1);
        MarketOffer capturedMarketOffer = marketOffers.get(0);
        assertThat(capturedMarketOffer.marketType()).isEqualTo(MarketType.PRIMARY);
        assertThat(capturedMarketOffer.priceBlocks()).hasSize(1);
    }

    @Test
    void should_have_not_found_when_market_offer_is_absent() throws Exception {
        mvc.perform(get("/api/market/offer/{type}", MarketType.PRIMARY).contentType("application/json")).andExpect(status().isNotFound());
    }

}