package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import com.nikolidakis.requests.GetImageRequest;
import com.nikolidakis.responses.GetImagesResponse;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.ImageServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.nikolidakis.models.constants.LogConstants.IMAGE_CONTROLLER;
import static com.nikolidakis.models.constants.LogConstants.UPLOAD_IMAGE;
import static com.nikolidakis.models.constants.StatusCodes.SUCCESS;

@RestController
@RequestMapping("/image")
@Data
@Slf4j
public class AuctionImageController {

    @Autowired
    private ImageServices imageServicesImpl;

    @PostMapping(value = "/upload")
    public Response uploadImage(@RequestParam("imageFile") MultipartFile imageFile,
                                @RequestParam String token, long auctionId) throws ImageException,
            AuthenticateException,
            AuctionException {
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "Ready to upload a new image");
        imageServicesImpl.save(imageFile, token, auctionId);
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "The image has been saved successfully");
        return new Response(SUCCESS, "The image has been saved successfully");
    }

    @PostMapping(value = "/getimages",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response getImages(@Valid @RequestBody GetImageRequest request) throws IOException, ImageException {
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "Ready to get the image message");
        List<byte[]> images = imageServicesImpl.getImages(request.getAuctionId());
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "The image/images has been retrieved successfully");
        return new GetImagesResponse(SUCCESS, "Array of bytes with the Images has been returned successfully", images);
    }

}
