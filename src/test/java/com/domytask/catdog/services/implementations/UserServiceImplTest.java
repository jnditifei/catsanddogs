package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.UserRepository;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepo;

    @Mock
    WalletRepository walletRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save() throws InvalidEntityToPersistException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userRepo.save(any())).thenReturn(user);
        assertEquals("username", userService.save(user).getUserName());
    }

    @Test
    void saveInvalidEntityToPersist() {
        UserEntity user = new UserEntity();
        user.setUserId(1L);
        assertThrows(InvalidEntityToPersistException.class, ()-> userService.save(user));
    }

    @Test
    void Update() throws NotFoundEntityException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenReturn(user);
        assertEquals(user, userService.update(user));
    }

    @Test
    void updateNotFoundEntity() {
        UserEntity user = new UserEntity();
        assertThrows(NotFoundEntityException.class, ()-> userService.update(user));
    }

    @Test
    void taskReservation() throws NotAuthorizeActionException, NotFoundEntityException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        task.setTaskStage(TaskStageEnum.ONE);
        List<TaskEntity> tasks = new ArrayList<>();
        user.setReservedTask(tasks);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.save(any())).thenReturn(user);
        assertEquals(user, userService.taskReservation(task, user));
    }

    @Test
    void taskReservationNotAuthorizeException() {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        TaskEntity task = new TaskEntity(false, "url", 0.00F, StatusEnum.RELEASE);
        assertThrows(NotAuthorizeActionException.class, ()-> userService.taskReservation(task, user));
    }

    @Test
    void taskReservationNotFoundEntity(){
        UserEntity user = new UserEntity();
        TaskEntity task = new TaskEntity(false, "url", 0.00F, StatusEnum.RELEASE);
        assertThrows(NotFoundEntityException.class, () -> userService.taskReservation(task, user));
    }

    @Test
    void getById() throws NotFoundEntityException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        assertEquals(user, userService.getById(1L));
    }

    @Test
    void getByIdNotFound(){
        Assertions.assertThrows(NotFoundEntityException.class, ()-> userService.getById(1L));
    }

    @Test
    void all() {
    }

    @Test
    void delete() {
        Assertions.assertThrows(NotFoundEntityException.class, ()-> userService.delete(1L));
    }
}