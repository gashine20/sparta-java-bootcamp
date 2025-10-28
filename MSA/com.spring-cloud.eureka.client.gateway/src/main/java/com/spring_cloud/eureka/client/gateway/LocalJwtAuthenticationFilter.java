package com.spring_cloud.eureka.client.gateway;

import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LocalJwtAuthenticationFilter implements GlobalFilter {

	@Value("${service.jwt.secret-key}")
	private String secretKey;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();
		if (path.equals("/auth/signIn") || path.equals("/auth/signUp")) {
			return chain.filter(exchange);
		}

		String token = extractToken(exchange);

		if (token == null || !validateToken(token, exchange)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		// Jws<Claims> claimsJws = Jwts.parser().unsecured()
		// 	.build().parseSignedClaims(token);

		// log.info("#####payload :: " + claimsJws.getPayload().toString());
		// Claims claims = claimsJws.getPayload();

		Map<String, Object> claims = extractClaimsWithoutSignature(token);
		ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
			.header("X-User-Id", claims.get("user_id").toString())
			.header("X-Role", claims.get("role").toString())
			.build();

		ServerWebExchange serverWebExchange = exchange.mutate().request(serverHttpRequest).build();
		return chain.filter(serverWebExchange);
	}

	public static Map<String, Object> extractClaimsWithoutSignature(String token) {
		try {
			// JWT는 header.payload.signature 구조
			String[] parts = token.split("\\.");
			if (parts.length < 2) {
				throw new IllegalArgumentException("Invalid JWT token");
			}

			// payload(Base64URL decode)
			String payload = new String(Decoders.BASE64URL.decode(parts[1]));

			// JSON → Map 변환
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(payload, Map.class);
		} catch (DecodingException e) {
			throw new RuntimeException("Failed to decode JWT payload", e);
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse JWT", e);
		}
	}

	private String extractToken(ServerWebExchange exchange) {
		String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}
		return null;
	}

	private boolean validateToken(String token, ServerWebExchange exchange) {
		try {
			SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
			Jwts.parser()
				.verifyWith(key)
				.build();
			// 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}