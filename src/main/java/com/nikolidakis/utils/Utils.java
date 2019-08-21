package com.nikolidakis.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Utils {
    public static String md5(String text) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        md.update(text.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter
                .printHexBinary(digest).toUpperCase();
    }

    public static LocalDateTime stringDateToLocalDate(String stringDateTime) {
        //check if the auction has ended
        int year = Integer.parseInt(stringDateTime.substring(0, 4));
        int month = Integer.parseInt(stringDateTime.substring(5, 7));
        int day = Integer.parseInt(stringDateTime.substring(8, 10));
        LocalDate date = LocalDate.of(year, month, day);

        int hour = Integer.parseInt(stringDateTime.substring(11, 13));
        int mins = Integer.parseInt(stringDateTime.substring(14));
        LocalTime time = LocalTime.of(hour, mins, 0, 0);

        return LocalDateTime.of(date, time);
    }


}
