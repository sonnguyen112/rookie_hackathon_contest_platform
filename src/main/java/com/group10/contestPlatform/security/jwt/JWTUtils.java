package com.group10.contestPlatform.security.jwt;



import java.security.InvalidParameterException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;


import com.group10.contestPlatform.entities.User;
import io.jsonwebtoken.io.Encoders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.HashMap;

import io.jsonwebtoken.io.Decoders;
import java.security.Key;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTUtils {
	private static Logger logger = LoggerFactory.getLogger(JWTUtils.class);
	@Value("${gipsy.app.jwtSecret}")
	private String secretKey;

	@Value("${gipsy.app.jwtExpirationMs}")
	private int expiration;

//	@Value("${application.security.jwt.refresh-token.expiration}")
//	private long refreshExpiration;


//	public String generateRefreshToken(
//			UserDetails userDetails
//	) {
//		return buildToken(new HashMap<>(), userDetails, refreshExpiration);
//	}

	public String generateToken(User user) throws Exception{
		//properties => claims
		Map<String, Object> claims = new HashMap<>();
		//this.generateSecretKey();
		claims.put("userName", user.getUsername());
		try {
			String token = Jwts.builder()
					.setClaims(claims) //how to extract claims from this ?
					.setSubject(user.getUsername())
					.setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
					.signWith(getSignInKey(), SignatureAlgorithm.HS256)
					.compact();
			return token;
		}catch (Exception e) {
			//you can "inject" Logger, instead System.out.println
			throw new InvalidParameterException("Cannot create jwt token, error: "+e.getMessage());
			//return null;
		}
	}
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	private String generateSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] keyBytes = new byte[32]; // 256-bit key
		random.nextBytes(keyBytes);
		String secretKey = Encoders.BASE64.encode(keyBytes);
		return secretKey;
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = this.extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	//check expiration
	public boolean isTokenExpired(String token) {
		Date expirationDate = this.extractClaim(token, Claims::getExpiration);
		return expirationDate.before(new Date());
	}
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	public boolean validateToken(String token, UserDetails userDetails) {
		try {
			String userName = extractUsername(token);
//			Token existingToken = tokenRepository.findByToken(token);
//			if(existingToken == null || existingToken.isRevoked() == true) {
//				return false;
//			}
			return (userName.equals(userDetails.getUsername()))
					&& !isTokenExpired(token);
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}


}
