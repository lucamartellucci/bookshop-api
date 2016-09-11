package io.lucci.bookshop.security.util;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class SecurityUtils {
	
	public static HttpHeaders createBasicAuthHeaders(String username, String password) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", "Basic " + new String(Base64.encodeBase64((username + ":" + password).getBytes(Charset.forName("US-ASCII")))));
		return httpHeaders;
	}

	public static HttpHeaders createJwtAuthHeaders(String token) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set("Authorization", token);
		return httpHeaders;
	}
}
