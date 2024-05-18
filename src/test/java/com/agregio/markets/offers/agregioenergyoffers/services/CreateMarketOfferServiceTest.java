package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.HourRange;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketOfferBlock;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.MarketOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

class CreateMarketOfferServiceTest {

    @Mock
    private MarketOfferRepository marketOfferRepository;

    @InjectMocks
    private CreateMarketOfferService createMarketOfferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_and_save_market_offer() {
        // Given
        MarketType marketType = MarketType.PRIMARY;
        MarketOfferBlock block = MarketOfferBlock.builder()
                .hourRange(HourRange.builder()
                        .from(LocalTime.of(0, 0))
                        .to(LocalTime.of(3, 0))
                        .build())
                .floorPrice(100.0)
                .producerParkIds(List.of("producer1"))
                .build();
        List<MarketOfferBlock> priceBlocks = List.of(block);

        // When
        createMarketOfferService.createMarketOffer(marketType, priceBlocks);

        // Then
        ArgumentCaptor<MarketOffer> marketOfferCaptor = ArgumentCaptor.forClass(MarketOffer.class);
        verify(marketOfferRepository).saveMarketOffer(marketOfferCaptor.capture());

        MarketOffer savedMarketOffer = marketOfferCaptor.getValue();
        assertThat(savedMarketOffer).isNotNull();
        assertThat(savedMarketOffer.marketType()).isEqualTo(marketType);
        assertThat(savedMarketOffer.priceBlocks()).isEqualTo(priceBlocks);
        assertThat(savedMarketOffer.id()).startsWith("M");
    }
}
