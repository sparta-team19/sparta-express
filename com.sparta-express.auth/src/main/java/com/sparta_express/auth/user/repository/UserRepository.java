package com.sparta_express.auth.user.repository;

import com.querydsl.core.types.Predicate;
import com.sparta_express.auth.user.entity.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface UserRepository extends JpaRepository<User, Long>,
    QuerydslPredicateExecutor<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeleted(String email, Boolean False);

    Page<User> findAll(Predicate predicate, Pageable pageable);
}
