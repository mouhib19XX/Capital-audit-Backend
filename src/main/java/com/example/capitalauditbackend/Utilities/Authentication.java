package com.example.capitalauditbackend.Utilities;
import com.example.capitalauditbackend.DOA.DatabaseConnector;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

public class Authentication {
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000); // Token expiry time (1 week)

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims decodeToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            //log parsing error
            return null;
        }
    }

    public static boolean tokenAuthenticator(Claims claim)
    {

        Date now = new Date();
        if (claim == null)
        {
            return false;
        }
        String username = claim.getSubject();
        if(checkExpired(claim))
        {
            DatabaseConnector db = new DatabaseConnector();
            return db.checkUsername(username);
        }
        else
        {
            return false;
        }
    }

    private static boolean checkExpired(Claims claim)
    {
        Date expirationDate = claim.getExpiration();
        Date currentDate = new Date();
        return expirationDate.after(currentDate);
    }
}
