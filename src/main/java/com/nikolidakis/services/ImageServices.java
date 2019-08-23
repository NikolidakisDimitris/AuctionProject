package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageServices {

    void save(MultipartFile imageFile, String token, long auctionId) throws ImageException, AuthenticateException, AuctionException;

    List<byte[]> getImages(long auctionId) throws IOException, ImageException;
}
