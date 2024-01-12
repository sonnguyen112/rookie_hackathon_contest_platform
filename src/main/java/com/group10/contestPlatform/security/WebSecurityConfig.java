package com.group10.contestPlatform.security;


import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;
import com.group10.contestPlatform.security.jwt.AuthTokenFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

	private final AuthTokenFilter jwtTokenFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.authorizeHttpRequests(authorizeRequests ->
						authorizeRequests
//								.requestMatchers("/api/**").permitAll()
//								.requestMatchers("/api/v1/quiz/create_quiz").hasAnyRole("ADMIN")
								.requestMatchers("/api/v1/quiz/get_all_quiz").hasAnyRole(Role.ADMIN)
								.anyRequest()
								.authenticated()
				)
//				.logout(logout ->
//						logout.logoutUrl("/api/v1/auth/logout")
//
//								.logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//				)
				.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}
}

