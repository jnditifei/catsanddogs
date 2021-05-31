package com.domytask.catdog.controllers;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestBody @Valid UserEntity userEntity){
        try{
            userEntity.setRole(RoleEnum.ENTRY);
            userService.save(userEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (InvalidEntityToPersistException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/register");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/modo/register", method =  RequestMethod.POST)
    public ResponseEntity<Object> registerModo(@RequestBody @Valid UserEntity userEntity){
        try{
            userEntity.setRole(RoleEnum.MODERATOR);
            userService.save(userEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (InvalidEntityToPersistException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/register");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> loginController(@RequestBody UserEntity user,
                                                  HttpServletResponse response){
        try{
            UserEntity found = userService.login(user.getEmail(), user.getPassword());
            Cookie cookie = new Cookie(found.getUserName(), found.getEmail());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            return new ResponseEntity<>(found, HttpStatus.OK);
        }catch (NotFoundEntityException e){
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update", method =  RequestMethod.PUT)
    public ResponseEntity<Object> update(@RequestBody UserEntity userEntity){
        try{
            return new ResponseEntity<>(userService.update(userEntity), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/update");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value =  "/reservation/{taskId}", method =  RequestMethod.PUT)
    public ResponseEntity<Object> taskReservation(@RequestBody UserEntity userEntity, @PathVariable long taskId){
        try{
            TaskEntity task = taskService.getById(taskId);
            UserEntity user = userService.getById(userEntity.getUserId());
            return new ResponseEntity<>(userService.taskReservation(task, user), HttpStatus.OK);
        }catch (NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/reservation/"+taskId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }catch (NotAuthorizeActionException e){
            e.getErrorDto().setStatus(403);
            e.getErrorDto().setPath("/reservation/"+taskId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable long userId){
        try {
            return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
        }catch (NotFoundEntityException e) {
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/getuser");
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{userId}", method =  RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable long userId){
        try{
            userService.delete(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(NotFoundEntityException e){
            e.getErrorDto().setStatus(400);
            e.getErrorDto().setPath("/delete"+userId);
            return new ResponseEntity<>(e.getErrorDto(), HttpStatus.BAD_REQUEST);
        }
    }
}
