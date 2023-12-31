package com.group10.contestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
