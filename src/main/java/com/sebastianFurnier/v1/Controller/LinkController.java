package com.sebastianFurnier.v1.Controller;

import com.sebastianFurnier.v1.Service.LinkServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RestController
public class LinkController {
    @Autowired
    private LinkServicesI linkService;

    @PostMapping("/shortener")
    public String createShortUrl(@RequestBody Map<String, String> request) throws NoSuchAlgorithmException {
        String url = request.get("url");
        return linkService.createShortUrl(url);
    }

    @GetMapping("/{shortId}")
    public String searchUrlByShortLink(@PathVariable String shortId){
        return linkService.searchUrlByShortId(shortId);
    }

}
