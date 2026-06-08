package com.porfolio.online_store.repositories.user;

import com.porfolio.online_store.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
