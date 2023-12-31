package com.group10.contestPlatform.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.group10.contestPlatform.entities.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.lang.NonNull;
import org.springframework.data.util.Pair;
@Component
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	private final UserDetailsService userDetailsService;
	@Autowired
	private JWTUtils jwtUtils;


	@Override
	protected void doFilterInternal(@NonNull  HttpServletRequest request,
									@NonNull HttpServletResponse response,
									@NonNull FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if(isBypassToken(request)) {
				filterChain.doFilter(request, response); //enable bypass
				return;
			}
			final String authHeader = request.getHeader("Authorization");
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return;
			}
			final String token = authHeader.substring(7);
			final String userName = jwtUtils.extractUsername(token);
			if (userName != null
					&& SecurityContextHolder.getContext().getAuthentication() == null) {
				User userDetails = (User) userDetailsService.loadUserByUsername(userName);
				if(jwtUtils.validateToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(
									userDetails,
									null,
									userDetails.getAuthorities()
							);
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
			filterChain.doFilter(request, response); //enable bypass
		}catch (Exception e) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		}

	}
	private boolean isBypassToken(@NonNull  HttpServletRequest request) {

		final List<Pair<String, String>> bypassTokens = Arrays.asList(

				Pair.of("/api/auth/signin", "POST"),
				Pair.of("/api/auth/signup", "POST"),
				Pair.of("/test", "GET")

		);
		for(Pair<String, String> bypassToken: bypassTokens) {
			if (request.getServletPath().contains(bypassToken.getFirst()) &&
					request.getMethod().equals(bypassToken.getSecond())) {
				return true;
			}
		}
		return false;
	}

}
