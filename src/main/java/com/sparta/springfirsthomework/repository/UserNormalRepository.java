package com.sparta.springfirsthomework.repository;

import com.sparta.springfirsthomework.domain.model.UserNormal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserNormalRepository extends JpaRepository<UserNormal, Long> {

    Optional<UserNormal> findByUsername(String username);
}
