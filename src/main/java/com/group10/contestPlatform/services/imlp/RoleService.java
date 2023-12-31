package com.group10.contestPlatform.services.imlp;



import com.group10.contestPlatform.entities.Role;
import com.group10.contestPlatform.entities.RoleEnum;
import com.group10.contestPlatform.repositories.RoleRepository;
import com.group10.contestPlatform.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Optional<Role> findByName(RoleEnum name) {
	
	     return roleRepository.findByName(name);
	}

}
