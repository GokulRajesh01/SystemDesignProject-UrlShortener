package sdproject.url_shortener.URLShortener.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import sdproject.url_shortener.URLShortener.entity.URLMapping;
import sdproject.url_shortener.URLShortener.exception.URLExpiredException;
import sdproject.url_shortener.URLShortener.exception.URLNotFoundException;
import sdproject.url_shortener.URLShortener.repository.URLRepository;
import sdproject.url_shortener.URLShortener.util.Base62Encoder;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Service
public class URLService {
    private final URLRepository urlRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public URLService(URLRepository urlRepository, RedisTemplate<String, String> redisTemplate) {
        this.urlRepository = urlRepository;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public String shortenUrl(String long_url){
        URLMapping url_obj = new URLMapping();
        url_obj.setActualUrl(long_url);

        // save to db according to Deep Dive Design
        urlRepository.save(url_obj);

        String shortCode = Base62Encoder.encode(url_obj.getId());
        //save to cache after db population
        redisTemplate.opsForValue().set("url:"+shortCode, url_obj.getActualUrl(), Duration.between(Instant.now(), url_obj.getExpiryDate()));

        return shortCode;
    }

    public String returnActualUrl(String shortcode){
        String cacheKey = "url:"+shortcode;

        // Check Cache
        String cacheUrl = redisTemplate.opsForValue().get(cacheKey);

        // Cache HIT
        if(cacheUrl != null){
            log.info("CACHE HIT");
            return cacheUrl;
        }

        log.info("CACHE MISS");

        // DB Lookup
        URLMapping url_obj = urlRepository.findById(Base62Encoder.decode(shortcode))
                .orElseThrow(() -> new URLNotFoundException("URL doesn't exist in the Database. Please Check and try again."));
        if(url_obj.getExpiryDate().compareTo(Instant.now()) < 0){
            throw new URLExpiredException("URL generated has expired. Please contact administrator to provide new access");
        }

        // Store in Cache
        redisTemplate.opsForValue().set(cacheKey, url_obj.getActualUrl(), Duration.between(Instant.now(), url_obj.getExpiryDate()));

        // Return Response
        return url_obj.getActualUrl();
    }
}
