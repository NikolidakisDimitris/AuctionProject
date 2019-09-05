package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.auctionrepository.AuctionRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.nikolidakis.models.constants.LogConstants.IMAGE_SERVICES;
import static com.nikolidakis.models.constants.LogConstants.UPLOAD_IMAGE;

@Service
@Data
@Slf4j
public class ImageServicesImpl implements ImageServices {

    private static final String FOLDER = "images/";

    private String SERVER_IP_TO_UPLOAD = "https://83.212.109.213:8080/";
    private String SERVER_IP_TO_GET = "http://83.212.109.213:8081/";


    @Autowired
    private UserServices userServices;
    @Autowired
    @Lazy
    private AuctionServices auctionServices;
    @Autowired
    private AuctionRepository auctionRepository;

    @Override
    public void save(MultipartFile imageFile, String token, Long auctionId) throws ImageException,
            AuthenticateException,
            AuctionException {
        log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "Ready to save the image ");

        //find user by token, and check if he/she it the seller. Only the seller can upload an image
        User user = userServices.findUserByToken(token);
        Auction auction = auctionServices.getAuctionById(auctionId);

        if (!user.getId().equals(auction.getSeller().getId())) {
            throw new ImageException("Ony the seller of the auction can upload an image");
        }

        try {
            byte[] bytes = imageFile.getBytes();
            log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "Before the folder save");

            //create directory if it doesn't exist
            if (!Files.exists(Paths.get(FOLDER))) {
                log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "The directory doesn't exist. ");
                Files.createDirectories(Paths.get(FOLDER));
                log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "The directory has been created ");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss SSS");
            String nowFormated = now.format(formatter).replace(" ", "_");


            String imageName = "auction_" + auctionId + "_" + nowFormated + ".jpg";
            Path path = Paths.get(FOLDER + imageName);
            Files.write(path, bytes);
            log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "The photo has been saved successfully");

            auction.setImagePath(SERVER_IP_TO_GET + imageName);
            auctionRepository.save(auction);

        } catch (IOException e) {
            log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "Exception occurred \n " + ExceptionUtils.getStackTrace(e));
            throw new ImageException("The image could not be parsed or saved");
        }
    }
}

