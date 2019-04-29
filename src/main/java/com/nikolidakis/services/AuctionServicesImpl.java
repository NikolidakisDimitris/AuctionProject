package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.repository.AuctionRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
@Data
@Slf4j
public class AuctionServicesImpl implements AuctionServices {

    @Autowired
    private final AuctionRepository repository;

    @Override
    public List<Auction> getAllAuctions() throws AuctionException {

        List<Auction> auctions = repository.findAll();
        if (isNull(auctions)) {
            throw new AuctionException("The are no auctions");
        }
        return auctions;
    }
}
