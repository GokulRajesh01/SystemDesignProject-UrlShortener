package sdproject.url_shortener.URLShortener.dto;

import jakarta.validation.constraints.NotBlank;

public record URLResponseBody (@NotBlank String shortUrl){
}