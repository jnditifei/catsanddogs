package com.domytask.catdog.controllers;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.PetsEnum;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;

    @Mock
    UserService userService;

    @InjectMocks
    TaskController taskController;

    @Test
    void create() throws InvalidEntityToPersistException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskService.save(any(TaskEntity.class))).thenReturn(task);
        taskController = spy(taskController);
        ResponseEntity response = taskController.create(task);
        verify(taskService, times(1)).save(any(TaskEntity.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void createInvalidEntity() throws InvalidEntityToPersistException {
        InvalidEntityToPersistException e = new InvalidEntityToPersistException("","","");
        doThrow(e).when(taskService).save(any(TaskEntity.class));
        assertThrows(InvalidEntityToPersistException.class, ()-> taskController.create(
                new TaskEntity(true, "url", 0.00F, StatusEnum.TODO)));
    }

    @Test
    void update() throws NotFoundEntityException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        task.setTaskId(1L);
        when(taskService.update(any(TaskEntity.class))).thenReturn(task);
        taskController = spy(taskController);
        ResponseEntity response = taskController.update(task);
        verify(taskService, times(1)).update(any(TaskEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateNotFound() throws NotFoundEntityException {
        NotFoundEntityException e = new NotFoundEntityException("","","");
        doThrow(e).when(taskService).update(any(TaskEntity.class));
        assertThrows(NotFoundEntityException.class, () -> taskController.update(
                new TaskEntity(true, "url", 0.00F, StatusEnum.TODO)));
    }

    @Test
    void taskFulfilment() throws NotAuthorizeActionException, NotFoundEntityException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        task.setTaskId(1L);
        task.setAnswer(PetsEnum.CAT);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        when(taskService.getById(anyLong())).thenReturn(task);
        when(taskService.taskFulfilment(any(TaskEntity.class),any(UserEntity.class)))
                .thenReturn(task);
        taskController = spy(taskController);
        ResponseEntity response = taskController.taskFulfilment(task,1L);
        verify(taskService, times(1)).taskFulfilment(
                any(TaskEntity.class), any(UserEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void taskFulfilmentNotFound() throws NotAuthorizeActionException, NotFoundEntityException {
        NotFoundEntityException e = new NotFoundEntityException("","","");
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskService.getById(anyLong())).thenReturn(task);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        doThrow(e).when(taskService).taskFulfilment(any(TaskEntity.class), any(UserEntity.class));
        task.setTaskId(1L);
        task.setAnswer(PetsEnum.CAT);
        assertThrows(NotFoundEntityException.class, () -> taskController.taskFulfilment(task, 1L));
    }

    @Test
    void TaskFulfilmentNotAuthorize() throws NotFoundEntityException, NotAuthorizeActionException {
        NotAuthorizeActionException e = new NotAuthorizeActionException("","","");
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskService.getById(anyLong())).thenReturn(task);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        doThrow(e).when(taskService).taskFulfilment(any(TaskEntity.class), any(UserEntity.class));
        task.setTaskId(1L);
        task.setAnswer(PetsEnum.CAT);
        assertThrows(NotAuthorizeActionException.class, ()-> taskController.taskFulfilment(task, 1L));
    }

    @Test
    void review() throws NotFoundEntityException, NotAuthorizeActionException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        task.setTaskId(1L);
        task.setAnswer(PetsEnum.CAT);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        when(taskService.getById(anyLong())).thenReturn(task);
        when(taskService.taskReview(any(TaskEntity.class),any(UserEntity.class)))
                .thenReturn(task);
        taskController = spy(taskController);
        ResponseEntity response = taskController.review(task,1L);
        verify(taskService, times(1)).taskReview(
                any(TaskEntity.class), any(UserEntity.class));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void reviewNotFoundException() throws NotFoundEntityException, NotAuthorizeActionException {
        NotAuthorizeActionException e = new NotAuthorizeActionException("","","");
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskService.getById(anyLong())).thenReturn(task);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(userService.getById(anyLong())).thenReturn(user);
        doThrow(e).when(taskService).taskReview(any(TaskEntity.class), any(UserEntity.class));
        task.setTaskId(1L);
        task.setAnswer(PetsEnum.CAT);
        ResponseEntity response = taskController.review(task, 1L);
        assertTrue(response.getBody().equals(e.getErrorDto()));
    }

    @Test
    void getById() throws NotFoundEntityException {
        TaskEntity task = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        when(taskService.getById(anyLong())).thenReturn(task);
        ResponseEntity response = taskController.getById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    void getByIdNotFound() throws NotFoundEntityException {
        NotFoundEntityException e = new NotFoundEntityException("Id Invalid", "", "");
        doThrow(e).when(taskService).getById(any(Long.class));
        assertThrows(NotFoundEntityException.class, ()-> taskController.getById(1L));
    }

    @Test
    void getAll() {
    }

    @Test
    void allAvailableForUser() throws NotFoundEntityException {
        TaskEntity task1 = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        TaskEntity task2 = new TaskEntity(true, "url", 0.00F, StatusEnum.TODO);
        List<TaskEntity> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        UserEntity user = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
        when(taskService.getAllTasksAvailableByStage(TaskStageEnum.ONE)).thenReturn(tasks);
        when(userService.getById(anyLong())).thenReturn(user);
        ResponseEntity response = taskController.allAvailableForUser(1L);
        assertEquals(tasks, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete() throws NotFoundEntityException {
        doNothing().when(taskService).delete(anyLong());
        assertEquals(HttpStatus.OK, taskController.delete(1L).getStatusCode());
        verify(taskService, times(1)).delete(anyLong());
    }

    @Test
    void deleteNotFound() throws NotFoundEntityException {
        NotFoundEntityException e = new NotFoundEntityException("ID invalid", "", "");
        doThrow(e).when(taskService).delete(anyLong());
        assertThrows(NotFoundEntityException.class, ()-> taskController.delete(1L).getStatusCode());
    }
}