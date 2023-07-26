package project_pet_backEnd.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
@Component
public class UserJwtUtil {
    private static final long EXPIRATION_TIME = TimeUnit.DAYS.toMillis(5);
    /**
     * JWT SECRET KEY
     */
    @Value("${User_Jwt_SECRET}")
    private String secret ;
    /**
     * 簽發JWT
     */
    public String generateToken(Map<String, String> userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put( "userId", userDetails.get("userId") );
        return Jwts.builder()
                .setClaims( claims )
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )
                .signWith(SignatureAlgorithm.HS256, secret )
                .compact();//返回的 JwtBuilder 物件的一個方法。
    }
    /**
     * 驗證JWT
     */
    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey( secret )
                    .parseClaimsJws( token )
                    .getBody();

        } catch (SignatureException e) {
            System.out.println("SignatureException");
            //throw new SignatureException("Invalid JWT signature.");
        }
        catch (MalformedJwtException e) {
            System.out.println("MalformedJwtException");
            // throw new MalformedJwtException("Invalid JWT token.");
        }
        catch (ExpiredJwtException e) {
            System.out.println("ExpiredJwtException");
            // throw e;
        }
        catch (UnsupportedJwtException e) {
            System.out.println("UnsupportedJwtException");
            // throw new UnsupportedJwtException("Unsupported JWT token");
        }
        catch (IllegalArgumentException e) {
            //  throw new IllegalArgumentException("JWT token compact of handler are invalid");
        }
        return null;
    }

    public  String getUserName(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
        return  claims.get("userId",String.class);
    }

    public String createJwt(String sub){
        return Jwts.builder()
                .setSubject(sub)
                .setExpiration( new Date( Instant.now().toEpochMilli() + EXPIRATION_TIME  ) )
                .signWith(SignatureAlgorithm.HS256, secret )
                .compact();
    }
}
