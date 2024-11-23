package com.sebastianFurnier.v1.Service;

import com.sebastianFurnier.v1.ExceptionHandler.BadUrlException;

import java.security.NoSuchAlgorithmException;

public interface ILinkServices {
    String searchUrlById(String id);
    public String searchIdByUrl(String url);
    public String createShortUrl(String url) throws NoSuchAlgorithmException, BadUrlException;
}
