package sdproject.url_shortener.URLShortener.util;

import lombok.experimental.UtilityClass;
import sdproject.url_shortener.URLShortener.exception.InvalidShortCodeException;

@UtilityClass
public class Base62Encoder {
    private static final String base62Characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long value){
        if(value == 0){
            return String.valueOf(base62Characters.charAt(0));
        }
        StringBuilder sb = new StringBuilder();
        while(value>0){
            int rem = (int)(value%62);
            sb.append(base62Characters.charAt(rem));
            value /= 62;
        }
        return sb.reverse().toString();
    }

    public long decode(String shortcode){
        long value = 0;
        for(char c: shortcode.toCharArray()){
            if(base62Characters.indexOf(c) == -1){
                throw new InvalidShortCodeException("The shortcode provided contains invalid characters. Please provide a valid URL");
            }
            value = value * 62 + base62Characters.indexOf(c);
        }
        return value;
    }
}
