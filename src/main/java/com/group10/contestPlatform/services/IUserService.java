package com.group10.contestPlatform.services;

import com.group10.contestPlatform.dtos.auth.ChangeProfileForm;
import com.group10.contestPlatform.dtos.auth.TakeUserCheatedResponse;
import com.group10.contestPlatform.dtos.auth.UserResponse;
import com.group10.contestPlatform.entities.User;

import org.springframework.data.domain.Pageable;
import paging.PagingAndSortingHelper;

import java.util.List;
import java.util.Optional;

public interface IUserService {
	 Optional<User> findByUsername(String name);
	    Boolean existsByUsername(String username);
	    Boolean existsByEmail(String email);
	    User save(User user);
	    List<User> findAll(Pageable pageable);
	    User findById(Long id);
	    void deleteById(Long id);
		List<User> findAll();
		double totalItem();
		void update(User user);
		User findByEmail(String email);

    String login(String username, String password) throws Exception;

	String updateResetPasswordToken(String token) throws Exception;

	User getUserDetailsFromToken(String token) throws Exception;

    void updatePassword(String token, String password) throws Exception;

	UserResponse listByPage(int pageNum, PagingAndSortingHelper helper, Integer roleId, Integer userCheated);

	User updateUser(Long id, ChangeProfileForm changeProfileForm) throws Exception;


}
