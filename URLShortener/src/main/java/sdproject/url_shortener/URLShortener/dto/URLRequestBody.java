package sdproject.url_shortener.URLShortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record URLRequestBody(
        @NotBlank
        @Pattern(regexp = "^https?://.*", message = "URL must start with http or https")
        String longUrl
){}