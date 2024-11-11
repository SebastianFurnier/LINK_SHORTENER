package com.sebastianFurnier.v1.Service;

import java.security.NoSuchAlgorithmException;

public interface LinkServicesI {
    String searchUrlByShortId(String shortId);
    public String searchUrlById(String id);
    public String createShortUrl(String url) throws NoSuchAlgorithmException;
}
