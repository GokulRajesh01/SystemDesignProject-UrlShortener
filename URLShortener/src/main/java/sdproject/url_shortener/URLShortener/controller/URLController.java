package sdproject.url_shortener.URLShortener.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sdproject.url_shortener.URLShortener.dto.URLRequestBody;
import sdproject.url_shortener.URLShortener.dto.URLResponseBody;
import sdproject.url_shortener.URLShortener.service.URLService;

import java.net.URI;

@RestController
@Validated
public class URLController {
    public final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/url/shorten")
    public ResponseEntity<URLResponseBody> shortenUrl(@Valid @RequestBody URLRequestBody request){
        URLResponseBody urlResponseBody = new URLResponseBody(urlService.shortenUrl(request.longUrl()));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(urlResponseBody);
    }

    @GetMapping("/{shortcode}")
    public ResponseEntity<Void> redirectShortUrl(@Size(min = 1, max = 10) @PathVariable String shortcode){
        return ResponseEntity
                .status(HttpStatus.TEMPORARY_REDIRECT)
                .location(URI.create(urlService.returnActualUrl(shortcode)))
                .build();
    }
}
