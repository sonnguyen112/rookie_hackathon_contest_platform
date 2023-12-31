package com.group10.contestPlatform;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.repositories.RoleRepository;
import com.group10.contestPlatform.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class ContestPlatformApplication {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ContestPlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Role adminRole = Role.builder().name("ADMIN").build();
			roleRepository.save(adminRole);
			User testUser = User.builder()
			.firstName("test")
			.lastName("test")
			.email("test")
			.username("admin")
			.password("admin")
			.roles(List.of(adminRole))
			.build();
			userRepository.save(testUser);
		};
	}
}
