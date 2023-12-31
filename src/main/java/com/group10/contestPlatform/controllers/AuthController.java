package com.group10.contestPlatform.controllers;


import com.group10.contestPlatform.dtos.ResponMessage;
import com.group10.contestPlatform.dtos.auth.JwtResponese;
import com.group10.contestPlatform.dtos.auth.LoginResponse;
import com.group10.contestPlatform.dtos.auth.SignInForm;
import com.group10.contestPlatform.dtos.auth.SignUpForm;
import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.LocalizationUtils;
import com.group10.contestPlatform.security.jwt.JWTUtils;
import com.group10.contestPlatform.services.IRoleService;
import com.group10.contestPlatform.services.IUserService;
import com.group10.contestPlatform.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.Set;
import jakarta.validation.Valid;
@RequestMapping("api/auth")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
	private final LocalizationUtils localizationUtils;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	@Autowired
	IUserService userService;

	@Autowired
	IRoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;
	
	@GetMapping(value = "/logout")
	public ResponseEntity<?> logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}

		return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
	}
	

	@Autowired
	JWTUtils jwtUtils;

	@PostMapping(value = "/signup")
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {

		if (userService.existsByUsername(signUpForm.getUsername())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}
		if (userService.existsByEmail(signUpForm.getEmail())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}


		User user = new User(signUpForm.getUsername(), signUpForm.getFirstName(), signUpForm.getEmail(),
				signUpForm.getLastName(), passwordEncoder.encode(signUpForm.getPassword()));

		Set<String> strRoles = signUpForm.getRoles();
		Set<Role> rolesEntity = new HashSet<>();

		strRoles.forEach(role -> {
			switch (role) {
			case "admin":
				Role adminRole = roleService.findByName(RoleEnum.ADMIN)
						.orElseThrow(() -> new RuntimeException("Role not found"));
				rolesEntity.add(adminRole);
				break;
			default:
				Role userRole = roleService.findByName(RoleEnum.USER)
						.orElseThrow(() -> new RuntimeException("Role not found"));
				rolesEntity.add(userRole);
			}
		});

		user.setRoles(rolesEntity);
		userService.save(user);

		return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "api/users/logoutDummy")
	@PreAuthorize("permitAll()")
	@ResponseStatus(HttpStatus.OK)
	public void logout() {
		// Dummy endpoint to point Spring Security to
		logger.debug("Logged out");
	}


	@PostMapping("/signin")
	public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));

			String token = userService.login(
					signInForm.getUsername(),
					signInForm.getPassword()

			);

			User userDetail = userService.getUserDetailsFromToken(token);




			return ResponseEntity.ok(LoginResponse.builder()
					.message("SUCCESS")
					.token(token)
					.username(userDetail.getUsername())
					.id(userDetail.getId())
					.first_name(userDetail.getFirstName())
					.last_name(userDetail.getLastName())
					.roles(userDetail.getAuthorities().stream().map(item -> item.getAuthority()).toList())
					.build());
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
		}
	}
}