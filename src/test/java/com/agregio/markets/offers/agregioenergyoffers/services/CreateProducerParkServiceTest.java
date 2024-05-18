package com.agregio.markets.offers.agregioenergyoffers.services;

import com.agregio.markets.offers.agregioenergyoffers.persistence.model.HourRange;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerPark;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkCapacityBlock;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.ProducerParkRepository;
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

class CreateProducerParkServiceTest {

    @Mock
    private ProducerParkRepository producerParkRepository;

    @InjectMocks
    private CreateProducerParkService createProducerParkService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void should_create_and_save_producer_park() {
        // Given
        String name = "Solar Park";
        ProducerParkType type = ProducerParkType.SOLAR_POWER;
        ProducerParkCapacityBlock capacityBlock = ProducerParkCapacityBlock.builder()
                .hourRange(new HourRange(LocalTime.of(0, 0), LocalTime.of(3, 0)))
                .capacity(100.0)
                .build();
        List<ProducerParkCapacityBlock> capacities = List.of(capacityBlock);

        // When
        createProducerParkService.createProducerPark(name, type, capacities);

        // Then
        ArgumentCaptor<ProducerPark> producerParkCaptor = ArgumentCaptor.forClass(ProducerPark.class);
        verify(producerParkRepository).saveProducerPark(producerParkCaptor.capture());

        ProducerPark savedProducerPark = producerParkCaptor.getValue();
        assertThat(savedProducerPark).isNotNull();
        assertThat(savedProducerPark.name()).isEqualTo(name);
        assertThat(savedProducerPark.type()).isEqualTo(type);
        assertThat(savedProducerPark.capacity()).isEqualTo(capacities);
        assertThat(savedProducerPark.id()).startsWith("P");
    }
}
