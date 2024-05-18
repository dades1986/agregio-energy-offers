package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.*;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.MarketOfferRepository;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.ProducerParkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FindTotalMarketOfferServiceTest {

    @Mock
    private MarketOfferRepository marketOfferRepository;

    @Mock
    private ProducerParkRepository producerParkRepository;

    @InjectMocks
    private FindTotalMarketOfferService findTotalMarketOfferService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_retrieve_total_market_offer() {
        // Given
        MarketType type = MarketType.PRIMARY;
        HourRange hourRange = HourRange.builder().from(LocalTime.of(0, 0)).to(LocalTime.of(3, 0)).build();
        MarketOfferBlock marketOfferBlock = MarketOfferBlock.builder()
                .hourRange(hourRange)
                .floorPrice(100.0)
                .producerParkIds(List.of("producer1"))
                .build();
        MarketOffer marketOffer = MarketOffer.builder()
                .id("M12345")
                .marketType(type)
                .priceBlocks(List.of(marketOfferBlock))
                .build();
        ProducerPark producerPark = ProducerPark.builder()
                .id("producer1")
                .name("Solar Park")
                .type(ProducerParkType.SOLAR_POWER)
                .capacity(List.of(ProducerParkCapacityBlock.builder()
                        .hourRange(hourRange)
                        .capacity(50.0)
                        .build()))
                .build();

        when(marketOfferRepository.findMarketByType(type)).thenReturn(Optional.of(marketOffer));
        when(producerParkRepository.findParkById("producer1")).thenReturn(Optional.of(producerPark));

        // When
        Optional<TotalMarketOffer> totalMarketOfferOptional = findTotalMarketOfferService.retrieve(type);

        // Then
        assertThat(totalMarketOfferOptional).isPresent();
        TotalMarketOffer totalMarketOffer = totalMarketOfferOptional.get();
        assertThat(totalMarketOffer.type()).isEqualTo(type);

        List<TotalMarketOfferTimeAndPriceBlock> blocks = totalMarketOffer.priceBlocks();
        assertThat(blocks).hasSize(1);
        TotalMarketOfferTimeAndPriceBlock block = blocks.get(0);
        assertThat(block.hourRange()).isEqualTo(hourRange);
        assertThat(block.floorPrice()).isEqualTo(100.0);
        assertThat(block.capacity()).isEqualTo(50.0);
    }

    @Test
    void should_return_empty_optional_when_no_market_offer_found() {
        // Given
        MarketType type = MarketType.PRIMARY;

        when(marketOfferRepository.findMarketByType(type)).thenReturn(Optional.empty());

        // When
        Optional<TotalMarketOffer> totalMarketOfferOptional = findTotalMarketOfferService.retrieve(type);

        // Then
        assertThat(totalMarketOfferOptional).isNotPresent();
    }
}
