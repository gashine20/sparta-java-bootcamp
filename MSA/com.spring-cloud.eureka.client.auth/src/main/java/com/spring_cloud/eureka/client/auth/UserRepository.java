package com.spring_cloud.eureka.client.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_cloud.eureka.client.auth.core.User;

public interface UserRepository extends JpaRepository<User, String> {
}
