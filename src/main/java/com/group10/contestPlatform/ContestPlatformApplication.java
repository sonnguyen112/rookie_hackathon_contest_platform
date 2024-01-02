package com.group10.contestPlatform;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication()
@RequiredArgsConstructor
public class ContestPlatformApplication {

	private final RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(ContestPlatformApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			Role adminRole = new Role();
			adminRole.setName("ADMIN");
			Role userRole = new Role();
			userRole.setName("USER");
			roleRepository.saveAll(List.of(adminRole, userRole));
		};
	}

}
