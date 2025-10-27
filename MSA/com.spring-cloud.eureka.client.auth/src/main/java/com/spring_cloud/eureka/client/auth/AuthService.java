package com.spring_cloud.eureka.client.auth;

import java.sql.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring_cloud.eureka.client.auth.core.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {

	@Value("${spring.application.name}")
	private String issuer;
	@Value("${service.jwt.access-expiration}")
	private Long accessExpiration;

	private final SecretKey secretKey;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(@Value("${service.jwt.secret-key}") String secretKey,
		UserRepository userRepository,
		PasswordEncoder passwordEncoder) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public String createAccessToken(String user_id, String role) {
		return Jwts.builder()
			// 사용자 ID를 클레임으로 설정
			.claim("user_id", user_id)
			.claim("role", role)
			// JWT 발행자를 설정
			.issuer(issuer)
			// JWT 발행 시간을 현재 시간으로 설정
			.issuedAt(new Date(System.currentTimeMillis()))
			// JWT 만료 시간을 설정
			.expiration(new Date(System.currentTimeMillis() + accessExpiration))
			// SecretKey를 사용하여 HMAC-SHA512 알고리즘으로 서명
			.signWith(secretKey, io.jsonwebtoken.SignatureAlgorithm.HS512)
			// JWT 문자열로 컴팩트하게 변환
			.compact();
	}

	public String signIn(String userId, String password) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id or password"));

		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("Invalid user Id or password");
		}

		return createAccessToken(user.getUserId(), user.getRole());
	}

	public User signUp(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

}
