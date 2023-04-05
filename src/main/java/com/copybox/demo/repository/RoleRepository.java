package com.copybox.demo.repository;

import com.copybox.demo.dto.ERole;
import com.copybox.demo.dto.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<Role> findByName(ERole name);
}
