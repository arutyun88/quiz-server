package com.arutyun.quiz_server.user.data.repository;

import com.arutyun.quiz_server.user.data.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PublicUserRepository extends JpaRepository<PublicUserEntity, UUID> {
}
