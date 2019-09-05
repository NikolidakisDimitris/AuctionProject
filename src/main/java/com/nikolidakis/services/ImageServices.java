package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    void save(MultipartFile imageFile, String token, Long auctionId) throws ImageException, AuthenticateException,
            AuctionException;
}
