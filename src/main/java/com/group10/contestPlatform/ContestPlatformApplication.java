package com.group10.contestPlatform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.repositories.RoleRepository;
import com.group10.contestPlatform.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication()
@RequiredArgsConstructor
public class ContestPlatformApplication {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

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

			User admin = User.builder().username("admin")
										.password(passwordEncoder.encode("admin"))
										.role(adminRole)
										.firstName("admin")
										.lastName("admin")
										.email("admin@gmail.com")
										.build();

			userRepository.save(admin);
		};
	}

}
