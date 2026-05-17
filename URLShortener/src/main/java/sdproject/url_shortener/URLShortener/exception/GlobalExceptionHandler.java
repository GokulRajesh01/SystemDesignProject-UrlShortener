package sdproject.url_shortener.URLShortener.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(URLNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFoundException(Exception exception){
        logger.error("Url not found Exception occurred : {}",exception.getMessage(),exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(URLExpiredException.class)
    public ResponseEntity<String> handleURLExpiredException(Exception exception){
        logger.error("Url expired Exception occurred : {}",exception.getMessage(),exception);
        return ResponseEntity
                .status(HttpStatus.GONE)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidShortCodeException.class)
    public ResponseEntity<String> handleInvalidShortCodeException(Exception exception){
        logger.error("Invalid Shortcode Exception occurred : {}",exception.getMessage(),exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception){
        String errorMessage = exception.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
        logger.error("Validation Exception occurred : {}",errorMessage,exception);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }
}
