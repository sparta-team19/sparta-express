package com.sparta_express.auth.user.repository;

import com.sparta_express.auth.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeleted(String email, Boolean False);
}
