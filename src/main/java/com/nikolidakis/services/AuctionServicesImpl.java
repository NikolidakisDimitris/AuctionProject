package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.repository.AuctionRepositoryImpl;
import com.nikolidakis.repository.UserRepository;
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

    private AuctionRepositoryImpl auctionRepository;
    private UserRepository userRepository;

    @Autowired
    private AuctionServicesImpl(AuctionRepositoryImpl auctionRepository, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }



    @Override
    public List<Auction> getAllAuctions() throws AuctionException {
        List<Auction> auctions = auctionRepository.findAll();
        return auctions;

    }

    @Override
    public List<Auction> getOpenAuctions() throws AuctionException {

        List<Auction> openAuctions = auctionRepository.getOpenAuctions();
        if (isNull(openAuctions)) {
            throw new AuctionException("There are no auction open at the moment");
        }
        return openAuctions;
    }

}
