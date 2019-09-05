package com.nikolidakis.controllers;

import com.nikolidakis.exceptions.AuctionException;
import com.nikolidakis.exceptions.AuthenticateException;
import com.nikolidakis.exceptions.ImageException;
import com.nikolidakis.responses.Response;
import com.nikolidakis.services.ImageServices;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public @ResponseBody
    Response uploadImage(@RequestParam("file") MultipartFile file,
                         @RequestParam String token, @RequestParam Long auctionId) throws ImageException,
            AuthenticateException, AuctionException {
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "Ready to upload a new image");
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "token: " + token);
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "auctionId: " + auctionId);
        imageServicesImpl.save(file, token, auctionId);
        log.info(IMAGE_CONTROLLER + UPLOAD_IMAGE + "The image has been saved successfully");
        return new Response(SUCCESS, "The image has been saved successfully");
    }

}
