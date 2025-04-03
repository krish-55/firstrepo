package com.library.model;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTImpl {

	SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
    String secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
	public String generateToken(String email)
	{
		return Jwts
				.builder()
				.setSubject(email)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+600000))
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();
	}
	public boolean validToken(String token) {
	    try {
	        Jws<Claims> claims = Jwts.parserBuilder()
	                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
	                .build()
	                .parseClaimsJws(token);

	        return !claims.getBody().getExpiration().before(new Date());
	    } catch (ExpiredJwtException e) {
	        System.out.println("Token has expired.");
	        return false;
	    } catch (Exception e) {
	        System.out.println("Invalid token.");
	        return false;
	    }
	}

	public String extractEmail(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
	            .build()
	            .parseClaimsJws(token)
	            .getBody()
	            .getSubject(); // The email is stored in the subject
	}

}
