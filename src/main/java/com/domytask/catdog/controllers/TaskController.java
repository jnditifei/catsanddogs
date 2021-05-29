package com.domytask.catdog.controllers;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
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

    @RequestMapping(value = "/create")
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

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public  ResponseEntity<Object> update(@RequestBody @Valid TaskEntity taskEntity){
        try{
            return new ResponseEntity<>(taskService.update(taskEntity), HttpStatus.CREATED);
        }catch(NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/update");
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
