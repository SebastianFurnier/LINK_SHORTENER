package com.sebastianFurnier.v1.Service;

import com.sebastianFurnier.v1.ExceptionHandler.BadUrlException;

import java.security.NoSuchAlgorithmException;

public interface ILinkServices {
    String searchUrlById(String id);
    String searchIdByUrl(String url);
    String createShortUrl(String url) throws NoSuchAlgorithmException, BadUrlException;
}
