package com.domytask.catdog.controllers;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@RequestBody @Valid TaskEntity taskEntity) throws InvalidEntityToPersistException {
        taskService.save(taskEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestBody TaskEntity taskEntity) throws NotFoundEntityException {
        return new ResponseEntity<>(taskService.update(taskEntity), HttpStatus.OK);
    }
    @PutMapping(value = "/evaluation/{userId}")
    public ResponseEntity<Object> taskFulfilment(@RequestBody TaskEntity taskEntity, @PathVariable long userId) throws NotFoundEntityException, NotAuthorizeActionException {
        UserEntity user = userService.getById(userId);
        TaskEntity task = taskService.getById(taskEntity.getTaskId());
        task.setAnswer(taskEntity.getAnswer());
        return new ResponseEntity<>(taskService.taskFulfilment(task, user), HttpStatus.OK);
    }
    @PutMapping(value = "/review/{userId}")
    public ResponseEntity<Object> review(@RequestBody TaskEntity taskEntity, @PathVariable long userId){
        try{
            TaskEntity task = taskService.getById(taskEntity.getTaskId());
            UserEntity user = userService.getById(userId);
            task.setStatus(taskEntity.getStatus());
            return new ResponseEntity<>(taskService.taskReview(task, user), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(401);
            e.getErrorDto().setPath(userId+"/review");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }catch (NotAuthorizeActionException e){
            e.getErrorDto().setStatus(401);
            e.getErrorDto().setPath(userId+"/review");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/{taskId}")
    public ResponseEntity<Object> getById(@PathVariable long taskId) throws NotFoundEntityException {
        return new ResponseEntity<>(taskService.getById(taskId), HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAll() throws NotFoundEntityException {
        return new ResponseEntity<>(taskService.all(), HttpStatus.OK);

    }
    @GetMapping(value = "/allavailable/{userId}")
    public ResponseEntity<Object> allAvailableForUser(@PathVariable Long userId) throws NotFoundEntityException {
        UserEntity user = userService.getById(userId);
        if (user.getRole().equals(RoleEnum.ENTRY)) {
            return new ResponseEntity<>(taskService.getAllTasksAvailableByStage(TaskStageEnum.ONE), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(taskService.getAllTasksAvailableByStage(TaskStageEnum.TWO), HttpStatus.OK);
        }
    }
    @DeleteMapping(value = "/delete/{taskId}")
    public ResponseEntity<Object> delete(@PathVariable long taskId) throws NotFoundEntityException {
        taskService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
