package com.agregio.markets.offers.agregioenergyoffers.mappers;

import com.agregio.markets.offers.agregioenergyoffers.dtos.CreateProducerParkCapacityBlockPayload;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.HourRange;
import com.agregio.markets.offers.agregioenergyoffers.persistence.model.ProducerParkCapacityBlock;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProducerParkMapper {

    public static List<ProducerParkCapacityBlock> mapPayloadListToModelList(List<CreateProducerParkCapacityBlockPayload> prodCapacities) {
        List<ProducerParkCapacityBlock> capacities = new ArrayList<>();
        prodCapacities.forEach(prodCap -> capacities.add(new ProducerParkCapacityBlock(HourRange
                .builder()
                .from(prodCap.hour().from())
                .to(prodCap.hour().to())
                .build(), prodCap.capacity())));
        return capacities;
    }
}
