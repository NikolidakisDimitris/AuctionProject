package com.nikolidakis.services;

import com.nikolidakis.models.Bid;
import com.nikolidakis.repository.BidRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@Slf4j
public class BidServicesImpl implements BidServices {

    @Autowired
    private final BidRepository repository;

    public List<Bid> getBids() {
        return (List<Bid>) repository.findAll();

    }
}
