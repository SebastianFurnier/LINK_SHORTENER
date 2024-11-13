package com.sebastianFurnier.v1.Service;

import java.security.NoSuchAlgorithmException;

public interface ILinkServices {
    String searchUrlById(String id);
    public String searchUrlByUrl(String url);
    public String createShortUrl(String url) throws NoSuchAlgorithmException;
}
