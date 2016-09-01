package io.lucci.bookshop.security.jwt;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.lucci.bookshop.model.UserBuilder;
import io.lucci.bookshop.security.SimpleUserDetails;

@Component
public class JwtTokenUtil implements Serializable {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = 1L;

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
    public static final String CLAIM_KEY_AUTHORITY = "authority";


    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;
    
    @Value("${jwt.issuer}")
    private String issuer;
    
    
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        if (userDetails.getAuthorities() != null) {
        	StringJoiner sj = new StringJoiner(",");
        	for (GrantedAuthority authority : userDetails.getAuthorities()) {
        		sj.add(authority.getAuthority());
        	}
        	claims.put(CLAIM_KEY_AUTHORITY, sj.toString());
        }
        
        return Jwts.builder()
    	        .setClaims(claims)
    	        .setIssuer(issuer)
    	        .setExpiration(generateExpirationDate())
    	        .signWith(SignatureAlgorithm.HS512, secret)
    	        .compact();
    }
    
    
    public boolean validateToken(String token) {
    	try {
    		Jwts.parser()
    			.setSigningKey(secret)
    			.requireIssuer(issuer)
    			.parse(token);
    		
    	} catch (ExpiredJwtException e) {
    		LOGGER.warn("expired token {}", token);
    		return false;
    	} catch (SignatureException e) {
    		LOGGER.warn("invalid token {}", token);
    		return false;
    	} catch (MalformedJwtException | IllegalArgumentException e) {
    		LOGGER.warn("malformed token {}", token);
    		return false;
    	} 
    	LOGGER.debug("valid token {}", token);
    	return true;
    }
    
    
    public UserDetails getUserDetailFromToken(String token) {
    	if (token == null) return null;

    	try {
    		Claims claims = Jwts.parser()
    			.setSigningKey(secret)
    			.requireIssuer(issuer)
    			.parseClaimsJws(token).getBody();
    	
    		UserDetails userDetails = new SimpleUserDetails(UserBuilder.user()
        		.withUsername(claims.get(CLAIM_KEY_USERNAME, String.class))
        		.withRoles(claims.get(CLAIM_KEY_AUTHORITY, String.class))
        		.build()
        	);
        	return userDetails;
    		
    	} catch (Exception e) {
    		LOGGER.warn("invalid token {}", token);
    		return null;
    	} 
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
    
    

    
    public String refreshToken(String token) {
        //TODO
        return token;
    }
    

    

    
    
	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}
    
    
    
}