package com.group10.contestPlatform.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group10.contestPlatform.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
