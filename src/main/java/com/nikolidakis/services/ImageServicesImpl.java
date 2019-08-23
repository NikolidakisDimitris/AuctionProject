package com.nikolidakis.services;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import com.nikolidakis.models.Auction;
import com.nikolidakis.models.AuctionImage;
import com.nikolidakis.models.User;
import com.nikolidakis.repository.auctionimagerepository.AuctionImageRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@Service
@Data
@Slf4j
public class ImageServicesImpl implements ImageServices {

    private static final String FOLDER = "images/";

    @Autowired
    private AuctionImageRepository imageRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private AuctionServices auctionServices;

    @Override
    public void save(MultipartFile imageFile, String token, long auctionId) throws ImageException, AuthenticateException,
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
            String nowFormated = now.format(formatter);

            String imageName = "auction_" + auctionId + "_" + nowFormated + ".jpg";
            Path path = Paths.get(FOLDER + imageName);
            Files.write(path, bytes);
            log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "The photo has been saved successfully");
            AuctionImage image = imageRepository.save(new AuctionImage(null, imageName, path.toString()));
        } catch (IOException e) {
            log.info(IMAGE_SERVICES + UPLOAD_IMAGE + "Exception occured \n " + ExceptionUtils.getStackTrace(e));
            throw new ImageException("The image could not be parsed or saved");
        }
    }

    @Override
    public List<byte[]> getImages(long auctionId) throws IOException, ImageException {
        log.info(IMAGE_SERVICES + GET_IMAGES + "ready to get the images");

        List<AuctionImage> paths = imageRepository.findAllImagePathsForAuctionId(auctionId);

        if (isEmpty(paths)) {
            throw new ImageException("This auction has no images");
        }

        List<byte[]> images = new ArrayList<>();
        for (AuctionImage current : paths) {

            byte[] bytes = getImageFromPath(current.getImagePath());
            images.add(getImageFromPath(current.getImagePath()));
        }
        return images;
    }

    //Gets the path and returns an array of bytes
    private byte[] getImageFromPath(String path) throws IOException, ImageException {
        InputStream in = new FileInputStream(path);
        System.out.println("Get Images From path" + in);
        if (isEmpty(in)) {
            throw new ImageException("The path is null");
        }
        return IOUtils.toByteArray(in);
    }

}

