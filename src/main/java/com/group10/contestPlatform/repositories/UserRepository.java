package com.group10.contestPlatform.repositories;


import com.group10.contestPlatform.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	User findByEmail(String email);
	public User findByResetPasswordToken(String token);

	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ',"
			+ " u.lastName) LIKE %?1%")
	public Page<User> findAll(String keyword, Pageable pageable);
	@Query("SELECT u FROM User u WHERE u.role.id = :roleId AND CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' ', u.lastName) LIKE %:keyword%")
	public Page<User> searchByRoleAndKeyword(@Param("roleId") Integer roleId, @Param("keyword") String keyword, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.role.id = :roleId")
	public Page<User> fillByRole(@Param("roleId") Integer roleId, Pageable pageable);

	@Query("SELECT DISTINCT u FROM User u " +
			"JOIN u.takes t " +
			"WHERE t.cheat = true")
	Page<User> getAllUsersCheated(Pageable pageable);


	@Query("SELECT DISTINCT u FROM User u " +
			"JOIN u.takes t " +

			"WHERE u.role.id = :roleId " +
			"AND LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"AND t.cheat = true")
	Page<User> searchByRoleAndKeywordAndUserCheated(
			@Param("roleId") Integer roleId,
			@Param("keyword") String keyword,
			Pageable pageable);

	@Query("SELECT DISTINCT u FROM User u " +
			"JOIN u.takes t " +
			"WHERE LOWER(u.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			"AND t.cheat = true")
	Page<User> getAllUsersCheatedWithKeyword(
			@Param("keyword") String keyword,
			Pageable pageable);
	@Query("SELECT DISTINCT u FROM User u " +
			"JOIN u.takes t " +
			"WHERE u.role.id = :roleId " +
			"AND t.cheat = true")
	Page<User> getAllUsersCheatedWithRole(
			@Param("roleId") Integer roleId,
			Pageable pageable);

}
