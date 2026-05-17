package sdproject.url_shortener.URLShortener.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sdproject.url_shortener.URLShortener.exception.InvalidShortCodeException;

public class Base62EncoderTest {
    @Test
    public void shouldEncodeCorrectly(){
        Assertions.assertEquals("cb", Base62Encoder.encode(125));
    }

    @Test
    public void shouldDecodeCorrectly(){
        Assertions.assertEquals(125, Base62Encoder.decode("cb"));
    }

    @Test
    public void shouldThrowInvalidShortCodeException(){
        Assertions.assertThrows(InvalidShortCodeException.class, () -> Base62Encoder.decode("./##"));
    }
}
