package com.spring_cloud.eureka.client.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring_cloud.eureka.client.auth.core.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("/auth/signIn")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody SignInRequest signInRequest) {
		String token = authService.signIn(signInRequest.getUserId(), signInRequest.getPassword());
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/auth/signUp")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		User createdUser = authService.signUp(user);
		return ResponseEntity.ok(createdUser);
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class AuthResponse {
		private String access_token;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	static class SignInRequest {
		private String userId;
		private String password;
	}
}
