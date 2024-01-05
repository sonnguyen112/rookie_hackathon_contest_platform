package com.group10.contestPlatform.services.imlp;


import com.group10.contestPlatform.dtos.auth.ChangeProfileForm;
import com.group10.contestPlatform.dtos.auth.UserResponse;
import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.exceptions.ExpiredTokenException;
import com.group10.contestPlatform.exceptions.LocalizationUtils;
import com.group10.contestPlatform.repositories.RoleRepository;
import com.group10.contestPlatform.repositories.UserRepository;
import com.group10.contestPlatform.security.jwt.JWTUtils;
import com.group10.contestPlatform.services.IUserService;
import com.group10.contestPlatform.utils.CustomerRegisterUtil;
import com.group10.contestPlatform.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import paging.PagingAndSortingHelper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final PasswordEncoder passwordEncoder;
	private final LocalizationUtils localizationUtils;
	private final AuthenticationManager authenticationManager;
	private final JWTUtils jwtTokenUtil;
	public static final int USERS_PER_PAGE = 4;
	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public Boolean existsByUsername(String username) {

		return userRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.existsByEmail(email);
	}

	@Override
	public User save(User user) {


		return (User) userRepository.save(user);
	}

	

	@Override
	public User findById(Long id) {
		Optional<User> userOptional = userRepository.findById(id);
		return userOptional.orElse(null);
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);

	}

	@Override
	public List<User> findAll(Pageable pageable) {
		List<User> entities = userRepository.findAll(pageable).getContent();
		return entities;
	}

	@Override
	public List<User> findAll() {
		List<User> entities = userRepository.findAll();
		return entities;
	}

	@Override
	public double totalItem() {
		return 0;
	}


	@Override
	public void update(User user) {

	}


	@Override
	public Optional<User> findByUsername(String name) {
		return userRepository.findByUsername(name);
	}

	@Override
	public User findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public String login(String username, String password) throws Exception {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if(optionalUser.isEmpty()) {
			throw new DataNotFoundException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_USER_PASSWORD));
		}
		//return optionalUser.get();//muốn trả JWT token ?
		User existingUser = optionalUser.get();
		//check password
		if (existingUser.getGoogleAccountId() == 0
				) {
			if(!passwordEncoder.matches(password, existingUser.getPassword())) {
				throw new BadCredentialsException(localizationUtils.getLocalizedMessage(MessageKeys.WRONG_USER_PASSWORD));
			}
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				username, password
		);

		//authenticate with Java Spring security
		authenticationManager.authenticate(authenticationToken);
		return jwtTokenUtil.generateToken(existingUser);
	}

	@Override
	public String updateResetPasswordToken(String email) throws Exception {
		User customer = userRepository.findByEmail(email);
		if (customer != null) {
			String token = RandomString.make(30);

			customer.setResetPasswordToken(token);
			userRepository.save(customer);

			return token;
		} else {
			throw new Exception("Could not find any customer with the email " + email);
		}
	}

	@Override
	public User getUserDetailsFromToken(String token) throws Exception {
		if(jwtTokenUtil.isTokenExpired(token)) {
			throw new ExpiredTokenException("Token is expired");
		}
		String phoneNumber = jwtTokenUtil.extractUsername(token);
		Optional<User> user = userRepository.findByUsername(phoneNumber);

		if (user.isPresent()) {
			return user.get();
		} else {
			throw new Exception("User not found");
		}
	}

	@Override
	public void updatePassword(String token, String newPassword) throws Exception {
		User customer = userRepository.findByResetPasswordToken(token);
		if (customer == null) {
			throw new Exception("No customer found: invalid token");
		}

		customer.setPassword(newPassword);
		customer.setResetPasswordToken(null);
		CustomerRegisterUtil.encodePassword(customer, passwordEncoder);

		userRepository.save(customer);
	}

	@Override
	public UserResponse listByPage(int pageNum, PagingAndSortingHelper helper, Integer roleId) {
		UserResponse result = new UserResponse();
		Pageable pageable = helper.createPageable(USERS_PER_PAGE, pageNum);
		String keyword = helper.getKeyword();
		Page<User> page = null;

		if (keyword != null && !keyword.isEmpty()) {

			if (roleId != null) {

				page = userRepository.searchByRoleAndKeyword(roleId, keyword, pageable);
			} else {
				page = userRepository.findAll(keyword, pageable);
			}
		} else {
			if (roleId != null ) {

				page = userRepository.fillByRole(roleId, pageable);
			} else {
				page = userRepository.findAll(pageable);
			}
		}

		helper.updateModelAttributes(pageNum, page,result);
		return result;
	}

	@Override
	public User updateUser(Long id, ChangeProfileForm changeProfileForm) throws Exception {
		User existingUser = userRepository.findById(id)
				.orElseThrow(() -> new DataNotFoundException("User not found"));
		String newUserName = changeProfileForm.getUsername();
		if (!existingUser.getUsername().equals(newUserName) &&
				userRepository.existsByUsername(newUserName)) {
			throw new DataIntegrityViolationException("Username already exists");
		}
		String newEmail = changeProfileForm.getEmail();
		if (!existingUser.getEmail().equals(newEmail) &&
				userRepository.existsByEmail(newEmail)) {
			throw new DataIntegrityViolationException("NewEmail already exists");
		}

		if (changeProfileForm.getFirstName() != null) {
			existingUser.setFirstName(changeProfileForm.getFirstName());
		}
		if (changeProfileForm.getLastName() != null) {
			existingUser.setLastName(changeProfileForm.getLastName());
		}
		if (changeProfileForm.getPassword() != null
				&& !changeProfileForm.getPassword().isEmpty()) {

			String newPassword = changeProfileForm.getPassword();
			String encodedPassword = passwordEncoder.encode(newPassword);
			existingUser.setPassword(encodedPassword);
		}else{
			existingUser.setPassword(existingUser.getPassword());
		}

		Role role =roleRepository.findById(Long.valueOf(changeProfileForm.getRoleId()))
				.orElseThrow(() -> new DataNotFoundException(
						localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DOES_NOT_EXISTS)));
		existingUser.setRole(role);
		return userRepository.save(existingUser);
	}


}
