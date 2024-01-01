package com.group10.contestPlatform.repositories;


import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleEnum name);
}
