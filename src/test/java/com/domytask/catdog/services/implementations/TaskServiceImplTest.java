package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.services.TaskService;
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
class TaskServiceImplTest {

    @Mock
    TaskRepository taskRepo;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void save() throws InvalidEntityToPersistException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskRepo.save(any())).thenReturn(task);
        assertEquals("url", taskService.save(task).getImageURL());
    }

    @Test
    void saveInvalidEntityToPersistException(){
        TaskEntity task = new TaskEntity();
        task.setTaskId(1L);
        assertThrows(InvalidEntityToPersistException.class, ()-> taskService.save(task));
    }

    @Test
    void update() throws NotFoundEntityException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        task.setTaskId(1L);
        when(taskRepo.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskRepo.save(any())).thenReturn(task);
        assertEquals(task, taskService.update(task));
    }

    @Test
    void updateNotFoundEntityException(){
        TaskEntity task = new TaskEntity();
        assertThrows(NotFoundEntityException.class, ()-> taskService.update(task));
    }

    @Test
    void taskFulfilment() throws NotAuthorizeActionException, NotFoundEntityException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        task.setTaskDoers(users);
        when(taskRepo.save(any())).thenReturn(task);
        assertEquals(TaskStageEnum.TWO, taskService.taskFulfilment(task, user).getTaskStage());
    }

    @Test
    void taskFulfilmentNotAuthorizeException() {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        UserEntity user2 = new UserEntity();
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        task.setTaskDoers(users);
        assertThrows(NotAuthorizeActionException.class, ()-> taskService.taskFulfilment(task, user2));
    }

    @Test
    void getAllTasksAvailableByStage() {
    }

    @Test
    void taskReview() throws NotAuthorizeActionException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.HOLD);
        UserEntity user = new UserEntity();
        user.setWallet(new WalletEntity());
        user.getWallet().setAvailableBalance(0.00F);
        user.getWallet().setOnHold(0.00F);
        UserEntity user1 = new UserEntity("username", "username@test.com", "password", RoleEnum.MODERATOR, new WalletEntity());
        List<UserEntity> users = new ArrayList<>();
        users.add(user);
        users.add(user1);
        task.setTaskDoers(users);
        when(taskRepo.save(any())).thenReturn(task);
        assertEquals(true, taskService.taskReview(task, user1).getReview());
    }

    @Test
    void getById() {
        Assertions.assertThrows(NotFoundEntityException.class, ()-> taskService.getById(1L));
    }

    @Test
    void getByIdNotFoundEntity(){
        assertThrows(NotFoundEntityException.class, ()-> taskService.getById(1L));
    }

    @Test
    void all() {
    }

    @Test
    void getAllAvailableTasksByStage() {
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundEntityException.class, ()-> taskService.delete(1L));
    }
}