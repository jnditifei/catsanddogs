package com.domytask.catdog.repositories;

import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepo;

    @BeforeEach
    public void dataSetup(){
        UserEntity user = new UserEntity("username","username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        userRepo.save(user);
    }

    @Test
    void findByEmail() {
        UserEntity user = userRepo.findByEmail("username@test.com").get();
        assertEquals("username", user.getUserName());
    }

    @Test
    void findByUserName() {
        UserEntity user = userRepo.findByUserName("username").get();
        assertEquals("username", user.getUserName());
    }
}