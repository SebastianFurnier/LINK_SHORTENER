package com.sebastianFurnier.v1.Controller;

import com.sebastianFurnier.v1.Service.ILinkServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LinkController {
    @Autowired
    private ILinkServices linkService;

    @PostMapping("/shortener")
    public ResponseEntity<Map<String, String>> createShortUrl(@RequestBody Map<String, String> request) throws NoSuchAlgorithmException {
        Map<String, String> response = new HashMap<>();
        String url = request.get("url");

        response.put("shortenedUrl", linkService.createShortUrl(url));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public String searchUrlByShortLink(@PathVariable String id){
        return linkService.searchUrlByUrl(id);
    }

}
