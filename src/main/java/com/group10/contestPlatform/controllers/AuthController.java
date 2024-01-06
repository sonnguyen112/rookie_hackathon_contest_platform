package com.group10.contestPlatform.controllers;


import com.group10.contestPlatform.dtos.ResponMessage;
import com.group10.contestPlatform.dtos.auth.*;
import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.exceptions.LocalizationUtils;
import com.group10.contestPlatform.security.jwt.JWTUtils;
import com.group10.contestPlatform.services.IRoleService;
import com.group10.contestPlatform.services.IUserService;
import com.group10.contestPlatform.services.imlp.ITakeService;
import com.group10.contestPlatform.services.imlp.SettingService;
import com.group10.contestPlatform.utils.CustomerForgetPasswordUtil;
import com.group10.contestPlatform.utils.CustomerRegisterUtil;
import com.group10.contestPlatform.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import jakarta.validation.Valid;

import paging.PagingAndSortingHelper;
import paging.PagingAndSortingParam;


@RequestMapping("api/auth")
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {
	private final LocalizationUtils localizationUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Value("${apiPrefix.client}")
	private String apiPrefix;
	@Autowired
	SettingService settingService;
	@Autowired
	IUserService userService;


	@Autowired
	ITakeService takeService;


	@Autowired
	IRoleService roleService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@GetMapping(value = "/users/page/{pageNum}")
	public ResponseEntity<?> showListUser(
			@PagingAndSortingParam(listName = "listUsers", moduleURL = "/users") PagingAndSortingHelper helper,
			 @PathVariable(name = "pageNum") int pageNum,
			@RequestParam(value = "roleId", required = false) Integer roleId,
			@RequestParam(value = "userCheated", required = false) Integer userCheated
	) {

		UserResponse result = userService.listByPage(pageNum, helper,roleId,userCheated);

		return new ResponseEntity<>(result, HttpStatus.OK);

	}

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
	public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) throws Exception {

		if (userService.existsByUsername(signUpForm.getUsername())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}
		if (userService.existsByEmail(signUpForm.getEmail())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}

		User user = new User(signUpForm.getUsername(), signUpForm.getFirstName(), signUpForm.getEmail(),
				signUpForm.getLastName(), passwordEncoder.encode(signUpForm.getPassword()));

		Role role = roleService.findById(signUpForm.getRoleId())
				.orElseThrow(() -> new DataNotFoundException(
						localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));

		if (role.getName().toUpperCase().equals(Role.ADMIN)) {
			throw new Exception("Không được phép đăng ký tài khoản Admin");
		}

		user.setRole(role);


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
	@PutMapping(value = "/update/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id,
									@Validated @RequestBody ChangeProfileForm changeProfileForm

	) {
		try {
		User user = userService.findById(id);
			if (user.getId() != id) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
			User updatedUser = userService.updateUser(id, changeProfileForm);
		return new ResponseEntity<>(new ResponMessage("yes"), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
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

	@PostMapping(value = "/forgot_password")
	public ResponseEntity<?> forgotPassword(HttpServletRequest request,@Valid @RequestBody ForgotPasswordForm forgotPasswordForm) throws Exception {


		if (!userService.existsByEmail(forgotPasswordForm.getEmail())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}
		String token = userService.updateResetPasswordToken(forgotPasswordForm.getEmail());
		String link = apiPrefix + "/reset_password?token=" + token;

		CustomerForgetPasswordUtil.sendEmail(link, forgotPasswordForm.getEmail(), settingService);


		return new ResponseEntity<>(new ResponMessage("200"), HttpStatus.OK);
	}
	@PostMapping(value = "/send_mail_user")
	public ResponseEntity<?> sendMailUser(HttpServletRequest request,@Valid @RequestBody SendMailForm sendMailForm) throws Exception {


		if (!userService.existsByEmail(sendMailForm.getEmail())) {
			return new ResponseEntity<>(new ResponMessage("nouser"), HttpStatus.OK);
		}
//		String token = userService.updateResetPasswordToken(forgotPasswordForm.getEmail());
//		String link = apiPrefix + "/reset_password?token=" + token;

		CustomerForgetPasswordUtil.sendEmailUserCheat(sendMailForm.getEmail(), sendMailForm.getContent(), settingService);


		return new ResponseEntity<>(new ResponMessage("200"), HttpStatus.OK);
	}

	@PostMapping(value = "/reset_password")
	public ResponseEntity<?> processResetPassword(@RequestParam(value = "token", required = true) String token,
												  @RequestParam(value = "password", required = true) String password) throws Exception {

		userService.updatePassword(token, password);

		return new ResponseEntity<>(new ResponMessage("200"), HttpStatus.OK);
	}

	@GetMapping("/detail_cheated/{userId}")
	public ResponseEntity<UserTakeDetailsResponseDTO> getTakeDetails(@PathVariable Long userId) {
		UserTakeDetailsResponseDTO  responseDTO = takeService.getTakeDetails(userId);

		if (responseDTO != null) {
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
