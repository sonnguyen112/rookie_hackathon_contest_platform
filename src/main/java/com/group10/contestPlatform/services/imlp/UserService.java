package com.group10.contestPlatform.services.imlp;


import com.group10.contestPlatform.entities.User;
import com.group10.contestPlatform.exceptions.DataNotFoundException;
import com.group10.contestPlatform.exceptions.ExpiredTokenException;
import com.group10.contestPlatform.exceptions.LocalizationUtils;
import com.group10.contestPlatform.repositories.UserRepository;
import com.group10.contestPlatform.security.jwt.JWTUtils;
import com.group10.contestPlatform.services.IUserService;
import com.group10.contestPlatform.utils.MessageKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
	private final PasswordEncoder passwordEncoder;
	private final LocalizationUtils localizationUtils;
	private final AuthenticationManager authenticationManager;
	private final JWTUtils jwtTokenUtil;
	@Autowired
	UserRepository userRepository;

	

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
		// TODO Auto-generated method stub
		return userRepository.save(user);
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
		return (int) userRepository.count();
		
	}

	@Override
	public void update(User user) {
		User currentUser = findById(user.getId());
		mergeUser(currentUser, user);
		 userRepository.save(currentUser);
	}
	private void mergeUser(User currentUser, User newUser) {
		currentUser.setUsername(newUser.getUsername());
		currentUser.setFirstName(newUser.getFirstName());
		currentUser.setLastName(newUser.getLastName());
		currentUser.setRoles(newUser.getRoles());
		currentUser.setEmail(newUser.getEmail());
//		currentUser.setPassword(currentUser.getPassword());
//		currentUser.setAvatar(currentUser.getAvatar());
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


}
