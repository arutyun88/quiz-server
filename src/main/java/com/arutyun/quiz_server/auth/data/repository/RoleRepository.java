package com.arutyun.quiz_server.auth.data.repository;

import com.arutyun.quiz_server.auth.data.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> getByName(String name);
}