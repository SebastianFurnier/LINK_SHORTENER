package com.sebastianFurnier.v1.Controller;

import com.sebastianFurnier.v1.ExceptionHandler.ExceptionDetails;
import com.sebastianFurnier.v1.ExceptionHandler.BadUrlException;
import com.sebastianFurnier.v1.Service.ILinkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
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

        Map<String, String> response = new HashMap<>();
        String url = request.get("url");

        response.put("shortenedUrl", linkService.createShortUrl(url));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public RedirectView searchUrlByShortLink(@PathVariable String id) throws BadUrlException {
        String url = linkService.searchUrlById(id);

        if (url == null) {
            throw new BadUrlException(
                    "Incorrect ID",
                    new ExceptionDetails("This ID doesn't exist.", "ERROR")
            );
        }
        return new RedirectView(url);
    }
}
