package com.sebastianFurnier.v1.Service;

import com.sebastianFurnier.v1.Model.ShortLink;
import com.sebastianFurnier.v1.Repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinkServices implements ILinkServices {
    @Autowired
    private LinkRepository linkRepository;

    private final String webUrl = System.getenv("WEB_URL");

    @Override
    public String createShortUrl(String url) throws NoSuchAlgorithmException {

        if(checkUrl(url))
            throw new RuntimeException();

        String urlAux = searchUrlById(url);

        if (urlAux != null)
            return urlAux;

        ShortLink newLinkModel = new ShortLink(shortUrl(url), url);
        linkRepository.save(newLinkModel);

        return "localhost:8080/" + newLinkModel.getId();
    }

    private boolean checkUrl(String url){
        Pattern pattern = Pattern.compile(webUrl, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }


    private String shortUrl(String url) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(url.getBytes());
        BigInteger number = new BigInteger(1, digest);
        String hashText = number.toString(36);
        return hashText.substring(0,6);
    }

    @Override
    public String searchUrlByUrl(String url){
        Optional<ShortLink> myUrl = linkRepository.findByUrl(url);

        if(myUrl.isEmpty())
            return null;

        ShortLink myLink = myUrl.get();

        return myLink.getId();
    }


    @Override
    public String searchUrlById(String id) {
        Optional<ShortLink> myUrl = linkRepository.findById(id);

        if(myUrl.isEmpty())
            return null;

        ShortLink myLink = myUrl.get();

        return myLink.getUrl();
    }
}
