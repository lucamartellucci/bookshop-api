package io.lucci.bookshop.security.jwt;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.lucci.bookshop.model.UserBuilder;
import io.lucci.bookshop.security.SimpleUserDetails;

public class JwtTokenUtilTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtilTest.class);
	private static final String SECRET = "qwertyuiop";
	private JwtTokenUtil jwtUtil;
	
	@Before
	public void setUp() throws Exception {
		jwtUtil = new JwtTokenUtil();
		jwtUtil.setSecret(SECRET);
		jwtUtil.setIssuer("acme inc.");
		jwtUtil.setExpiration(604800L);
	}

	@Test
	public void testGenerateTokenUserDetails() throws Exception {
		
		String token = jwtUtil.generateToken(buildUser("user001", Arrays.asList("ROLE_USER","ROLE_GOD")));
		LOGGER.info("Generated token is: {}", token);
		
		Claims claims = null;
		try {
            claims = Jwts.parser()
            	.setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        } catch (Exception e) {
            fail();
        }

		assertWithMessage("Claims is not null").that(claims).isNotNull();
		assertWithMessage("Authority Claim contains the role: ROLE_GOD")
			.that(claims.get(JwtTokenUtil.CLAIM_KEY_AUTHORITY, String.class)).contains("ROLE_GOD");
		assertWithMessage("Sub Claim contains: user001")
			.that(claims.get(JwtTokenUtil.CLAIM_KEY_USERNAME, String.class)).isEqualTo("user001");
	}

	@Test
	public void testValidateToken() throws Exception {
		String token = jwtUtil.generateToken(buildUser("user001", Arrays.asList("ROLE_USER","ROLE_GOD")));
		assertThat(jwtUtil.validateToken(token)).isTrue();
		assertThat(jwtUtil.validateToken(token.concat("xxxxx"))).isFalse();
		assertThat(jwtUtil.validateToken(null)).isFalse();
		jwtUtil.setExpiration(1L);
		String expiredToken = jwtUtil.generateToken(buildUser("user001", Arrays.asList("ROLE_USER","ROLE_GOD")));
		Thread.sleep(1000);
		assertThat(jwtUtil.validateToken(expiredToken)).isFalse();
	}

	
	
	private UserDetails buildUser(String username, List<String> roles) {
		UserDetails userDetails = new SimpleUserDetails(
			UserBuilder.user().withUsername(username).withRoles(roles).build());
		return userDetails;
	}
}
