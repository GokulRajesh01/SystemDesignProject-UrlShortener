package sdproject.url_shortener.URLShortener.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import sdproject.url_shortener.URLShortener.repository.URLRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class URLServiceTest {
    @Mock
    private URLRepository urlRepository;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private URLService urlService;

    @Test
    public void shouldReturnUrlFromCache(){
        // Set the values beforehand
        String shortCode = "g0";
        String cacheKey = "url:"+shortCode;
        String expectedUrl = "https://www.linkedin.com";

        // Setting opsForValues return object - Value Operations
        when(redisTemplate.opsForValue())
                .thenReturn(valueOperations);

        // Setting to return expected url
        when(valueOperations.get(cacheKey))
                .thenReturn(expectedUrl);

        String actualUrl = urlService.returnActualUrl(shortCode);
        Assertions.assertEquals(expectedUrl, actualUrl);

        verify(urlRepository, never()).findById(anyLong());
    }
}
