package com.agregio.markets.offers.agregioenergyoffers.controllers;

import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateMarketOfferPayload;
import com.agregio.markets.offers.agregioenergyoffers.dtos.MarketTypePayload;
import com.agregio.markets.offers.agregioenergyoffers.mappers.MarketOfferMapper;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.MarketType;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.TotalMarketOffer;
import com.agregio.markets.offers.agregioenergyoffers.services.CreateMarketOfferService;
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
@RequestMapping("/api/market/offer")
@Tag(name = "Market Offer", description = "Market offers api")
@RequiredArgsConstructor
public class MarketOfferController {
    private final CreateMarketOfferService createMarketOfferService;
    private final FindTotalMarketOfferService findTotalMarketOfferService;

    @Operation(summary = "Create a new market offer",
            description = "This endpoint create a new offer on the market, for that You have to choice the market type, threshold price, the time block and the chosen parks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Market offer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Void> createOffer(@RequestBody CreateMarketOfferPayload createMarketOfferPayload) {
        createMarketOfferService.createMarketOffer(MarketType.valueOf(createMarketOfferPayload.type().name()), MarketOfferMapper.mapPayloadListToModelList(createMarketOfferPayload.priceBlocks()));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @Operation(summary = "Get all offers from the market",
            description = "This endpoint retrieve all available offers from the market : all time blocks of all producers parks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all offers"),
            @ApiResponse(responseCode = "404", description = "No offer found")
    })
    @GetMapping(path = "/{type}")
    public ResponseEntity<TotalMarketOffer> findTotalMarketOffer(@PathVariable MarketTypePayload type) {
        return findTotalMarketOfferService.retrieve(MarketType.valueOf(type.name()))
                .map(totalMarketOffer -> new ResponseEntity<>(totalMarketOffer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
