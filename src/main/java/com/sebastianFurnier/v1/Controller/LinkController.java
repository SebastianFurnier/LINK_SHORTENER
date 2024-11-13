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

    @ExceptionHandler(BadUrlException.class)
    public ResponseEntity<Map<String, String>> handleBadUrlException(BadUrlException ex){
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Error", ex.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @PostMapping("/shortener")
    public ResponseEntity<Map<String, String>> createShortUrl(@RequestBody Map<String, String> request)
            throws NoSuchAlgorithmException {
        Map<String, String> response = new HashMap<>();
        String url = request.get("url");

        try{
            response.put("shortenedUrl", linkService.createShortUrl(url));
        }catch (BadUrlException ex){
            return handleBadUrlException(ex);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public RedirectView searchUrlByShortLink(@PathVariable String id){
        return new RedirectView(linkService.searchUrlById(id));
    }

}
