package sdproject.url_shortener.URLShortener.exception;

public class URLExpiredException extends RuntimeException {
    public URLExpiredException(String message) {
        super(message);
    }
}
