package com.domytask.catdog.repositories;

import com.domytask.catdog.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    public Optional<UserEntity> findByEmail(String email);

    public Optional<UserEntity> findByUserName(String username);

    public UserEntity findByEmailAndPassword(String email, String password);
}
