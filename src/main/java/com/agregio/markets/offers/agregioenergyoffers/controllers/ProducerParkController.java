package com.agregio.markets.offers.agregioenergyoffers.controllers;

import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateProducerParkPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.MarketTypePayload;
import com.agregio.markets.offers.agregioenergyoffers.mappers.ProducerParkMapper;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.TotalMarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.services.CreateProducerParkService;
import com.agregio.markets.offers.agregioenergyoffers.services.FindTotalMarketOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parcs")
@Tag(name = "Energy producer", description = "Energy producers api")
@RequiredArgsConstructor
public class ProducerParkController {
    private final CreateProducerParkService createProducerParkService;
    private final FindTotalMarketOfferService findTotalMarketOfferService;

    @Operation(summary = "Create a new energy producer",
            description = "This endpoint create a new energy producer, for that You have to choice the park type, park name, capacity of each block")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Energy park added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Void> createOffer(@RequestBody CreateProducerParkPayload createProducerParkPayload) {
        createProducerParkService.createProducerPark(createProducerParkPayload.name(), ProducerParkType.valueOf(createProducerParkPayload.type().name()), ProducerParkMapper.mapPayloadListToModelList(createProducerParkPayload.capacities()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Retrieve all energy parcs",
            description = "This endpoint retrieve all created energy parcs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all parcs"),
            @ApiResponse(responseCode = "404", description = "No park found")
    })
    @GetMapping(path = "/find")
    public ResponseEntity<TotalMarketOffer> findTotalMarketOffer(@PathVariable MarketTypePayload type) {
        return findTotalMarketOfferService.retrieve(MarketType.valueOf(type.name()))
                .map(totalMarketOffer -> new ResponseEntity<>(totalMarketOffer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
