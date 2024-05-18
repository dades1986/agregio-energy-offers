package com.agregio.markets.offers.agregioenergyoffers.controllers;

import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateProducerParkCapacityBlockPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateProducerParkPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.HourRangePayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.ProducerParkTypePayload;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.repositories.ProducerParkRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProducerParkControllerIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProducerParkRepository producerParkRepository;


    @Test
    void should_create_producer_park_when_posting_new_producer_park() throws Exception {

        // Given
        CreateProducerParkPayload createProducerParkCapacityBlockPayload = CreateProducerParkPayload.builder()
                .name("Solar Park")
                .type(ProducerParkTypePayload.SOLAR_POWER)
                .capacities(List.of(CreateProducerParkCapacityBlockPayload.builder()
                        .hour(HourRangePayload.builder()
                                .from(LocalTime.of(0, 0))
                                .to(LocalTime.of(3, 0))
                                .build())
                        .capacity(100.0)
                        .build()))
                .build();


        String content = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(createProducerParkCapacityBlockPayload);

        // When
        mvc.perform(post("/api/parcs/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated());

        // Then
        assertThat(producerParkRepository.findAllProducerParks()).hasSize(1);
        var savedPark = producerParkRepository.findAllProducerParks().get(0);
        assertThat(savedPark.name()).isEqualTo("Solar Park");
        assertThat(savedPark.type()).isEqualTo(ProducerParkType.SOLAR_POWER);
        assertThat(savedPark.capacity()).hasSize(1);
        var savedCapacity = savedPark.capacity().get(0);
        assertThat(savedCapacity.hourRange().from()).isEqualTo(LocalTime.of(0, 0));
        assertThat(savedCapacity.hourRange().to()).isEqualTo(LocalTime.of(3, 0));
        assertThat(savedCapacity.capacity()).isEqualTo(100);
    }

    @Test
    void should_return_not_found_when_no_total_market_offer_is_present() throws Exception {
        // Given When & Then
        mvc.perform(get("/api/parcs/find/{type}", MarketType.PRIMARY).contentType("application/json")).andExpect(status().isNotFound());
    }

}
