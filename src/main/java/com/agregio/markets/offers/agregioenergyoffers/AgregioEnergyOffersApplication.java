package com.agregio.markets.offers.agregioenergyoffers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.agregio.markets.offers.agregioenergyoffers"})
public class AgregioEnergyOffersApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgregioEnergyOffersApplication.class, args);
    }

}
