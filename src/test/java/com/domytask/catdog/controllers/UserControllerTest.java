package com.domytask.catdog.controllers;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.PetsEnum;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    TaskService taskService;

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @Test
    void register() throws InvalidEntityToPersistException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.save(any(UserEntity.class))).thenReturn(user);
        userController = spy(userController);
        ResponseEntity response = userController.register(user);
        verify(userService, times(1)).save(any(UserEntity.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void registerModo() throws InvalidEntityToPersistException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.save(any(UserEntity.class))).thenReturn(user);
        userController = spy(userController);
        ResponseEntity response = userController.registerModo(user);
        verify(userService, times(1)).save(any(UserEntity.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void update() throws NotFoundEntityException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        when(userService.update(any(UserEntity.class))).thenReturn(user);
        userController = spy(userController);
        ResponseEntity response = userController.update(user);
        verify(userService, times(1)).update(any(UserEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void taskReservation() throws NotFoundEntityException, NotAuthorizeActionException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        user.setUserId(1L);
        when(userService.getById(anyLong())).thenReturn(user);
        when(taskService.getById(anyLong())).thenReturn(task);
        when(userService.taskReservation(any(TaskEntity.class),any(UserEntity.class)))
                .thenReturn(user);
        userController = spy(userController);
        ResponseEntity response = userController.taskReservation(user, 1L);
        verify(userService, times(1)).taskReservation(
                any(TaskEntity.class), any(UserEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getUserById() throws NotFoundEntityException {
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        ResponseEntity response = userController.getUser(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    void delete() throws NotFoundEntityException {
        doNothing().when(userService).delete(anyLong());
        assertEquals(HttpStatus.OK, userController.delete(1L).getStatusCode());
        verify(userService, times(1)).delete(anyLong());
    }
}