package com.group10.contestPlatform.services;



import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;

import java.util.Optional;

public interface IRoleService {
	  Optional<Role> findByName(RoleEnum name);
}
