package com.sebastianFurnier.v1.Service;

import com.sebastianFurnier.v1.ExceptionHandler.ExceptionDetails;
import com.sebastianFurnier.v1.ExceptionHandler.BadUrlException;
import com.sebastianFurnier.v1.Model.ShortLink;
import com.sebastianFurnier.v1.Repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LinkServices implements ILinkServices {

    @Autowired
    private LinkRepository linkRepository;
    private final String webUrl = System.getenv("WEB_URL");

    @Override
    public String createShortUrl(String url) throws NoSuchAlgorithmException, BadUrlException {
        if (checkShortenedUrl(url)) {
            throw new BadUrlException(
                    "This URL is already shortened",
                    new ExceptionDetails("This URL isn't valid because it looks already shortened", "Error")
            );
        }

        String checkedHttpUrl = hasHttp(url);

        if (!validUrl(checkedHttpUrl)) {
            throw new BadUrlException(
                    "This URL is no correct.",
                    new ExceptionDetails("This is not a valid URL, try again.", "Error")
            );
        }

        String existingId = searchIdByUrl(checkedHttpUrl);

        if (existingId != null) {
            return existingId;
        }

        ShortLink newLinkModel = new ShortLink(makeShortUrl(checkedHttpUrl), checkedHttpUrl, 0);

        int i = 0;
        while(thereIsCollision(newLinkModel) && i <= 10){
            newLinkModel.setId(avoidCollision(newLinkModel.getId()));
            i++;
        }

        if (i == 10)
            return null;

        linkRepository.save(newLinkModel);

        return newLinkModel.getId();
    }

    private boolean validUrl (String url) {
        Pattern URL_PATTERN = Pattern.compile(
                "^(https?|ftp)://[\\w.-]+(?:\\.[\\w.-]+)+[/#?]?.*$",
                Pattern.CASE_INSENSITIVE
        );

        return URL_PATTERN.matcher(url).matches();
    }

    private boolean thereIsCollision(ShortLink newLinkModel){
        String url = newLinkModel.getUrl();
        String urlAux = searchUrlById(newLinkModel.getId());

        return url.equals(urlAux);
    }

    private String avoidCollision(String id){
        Random rand = new Random();
        int randomIndex = rand.nextInt(36);

        char randomSymbol;
        if (randomIndex < 10) {
            randomSymbol = (char) ('0' + randomIndex);
        } else {
            randomSymbol = (char) ('a' + randomIndex - 10);
        }

        return id + randomSymbol;
    }

    private boolean checkShortenedUrl(String url) {
        if (webUrl == null) {
            return true;
        }

        Pattern pattern = Pattern.compile(webUrl, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);

        return matcher.find();
    }

    private String hasHttp(String url) {
        Pattern pattern = Pattern.compile("http", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);

        return matcher.find() ? url : "https://" + url;
    }

    private String makeShortUrl(String url) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(url.getBytes());
        BigInteger number = new BigInteger(1, digest);
        String hashText = number.toString(36);

        return hashText.substring(0, 6);
    }

    @Override
    public String searchIdByUrl(String url) {
        Optional<ShortLink> myUrl = linkRepository.findByUrl(url);

        if (myUrl.isEmpty())
            return null;

        ShortLink myShortLink = myUrl.get();
        return myShortLink.getId();
    }

    @Override
    public String searchUrlById(String id) {
        Optional<ShortLink> myUrl = linkRepository.findById(id);

        if (myUrl.isEmpty())
            return null;

        ShortLink myShortLink = myUrl.get();

        myShortLink.setNumberOfClicks(myShortLink.getNumberOfClicks() + 1);
        linkRepository.save(myShortLink);
        return myShortLink.getUrl();
    }
}