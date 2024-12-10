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
import java.util.Optional;
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

        String existingId = searchIdByUrl(url);
        if (existingId != null) {
            return existingId;
        }

        String checkedHttpUrl = hasHttp(url);

        ShortLink newLinkModel = new ShortLink(makeShortUrl(checkedHttpUrl), checkedHttpUrl, 0);

        linkRepository.save(newLinkModel);

        return "https://" + webUrl + "/" + newLinkModel.getId();
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
        return myShortLink.getUrl();
    }
}