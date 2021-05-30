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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Object> create(@RequestBody @Valid TaskEntity taskEntity){
        try{
            taskService.save(taskEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(InvalidEntityToPersistException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/create");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody TaskEntity taskEntity){
        try{
            return new ResponseEntity<>(taskService.update(taskEntity), HttpStatus.OK);
        }catch(NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/update");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/evaluation/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> taskFulfilment(@RequestBody TaskEntity taskEntity, @PathVariable long userId){
        try{
            UserEntity user = userService.getById(userId);
            TaskEntity task = taskService.getById(taskEntity.getTaskId());
            task.setAnswer(taskEntity.getAnswer());
            return new ResponseEntity<>(taskService.taskFulfilment(task, user), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/evaluation/"+userId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }catch (NotAuthorizeActionException e){
            e.getErrorDto().setStatus(401);
            e.getErrorDto().setPath("/evaluation/"+userId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/review/{userId}", method =  RequestMethod.PUT)
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

    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getById(@PathVariable long taskId){
        try{
            return new ResponseEntity<>(taskService.getById(taskId), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/"+taskId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<Object> getAll(){
        try{
            return new ResponseEntity<>(taskService.all(), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/all");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/allavailable/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> allAvailableForUser(@PathVariable Long userId){
        try {
            UserEntity user = userService.getById(userId);
            if (user.getRole().equals(RoleEnum.ENTRY)) {
                return new ResponseEntity<>(taskService.getAllTasksAvailableByStage(TaskStageEnum.ONE), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(taskService.getAllTasksAvailableByStage(TaskStageEnum.TWO), HttpStatus.OK);
            }
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/allAvailableForUser");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{taskId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long taskId){
        try{
            taskService.delete(taskId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/all");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }
}
