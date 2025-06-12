package com.sebastianFurnier.v1.Controller;

import com.sebastianFurnier.v1.ExceptionHandler.ExceptionDetails;
import com.sebastianFurnier.v1.ExceptionHandler.BadUrlException;
import com.sebastianFurnier.v1.Service.ILinkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LinkController {

    @Autowired
    private ILinkServices linkService;

    private static final String ERROR_KEY = "Error";

    @ExceptionHandler(BadUrlException.class)
    public ResponseEntity<Map<String, String>> handleBadUrlException(BadUrlException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        ExceptionDetails details = ex.getDetails();

        errorResponse.put(ERROR_KEY, details.getUserMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @PostMapping("/shortener")
    public ResponseEntity<Map<String, String>> createShortUrl(
            @RequestBody Map<String, String> request) throws NoSuchAlgorithmException, BadUrlException {

        System.out.println("POST /shortener received with body: " + request);

        Map<String, String> response = new HashMap<>();
        String url = request.get("url");

        if (url == null || url.isEmpty()) {
            throw new BadUrlException("Missing URL",
                    new ExceptionDetails("The URL field is required.", "ERROR"));
        }

        String shortUrl = linkService.createShortUrl(url);
        response.put("shortenedUrl", shortUrl);

        System.out.println("Short URL generated: " + shortUrl);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, String>> searchUrlByShortLink(@PathVariable String id) throws BadUrlException {
        String url = linkService.searchUrlById(id);

        if (url == null) {
            throw new BadUrlException(
                    "Incorrect ID",
                    new ExceptionDetails("This ID doesn't exist.", "ERROR")
            );
        }

        Map<String, String> response = new HashMap<>();
        response.put("url", url);
        return ResponseEntity.ok(response);
    }
}
