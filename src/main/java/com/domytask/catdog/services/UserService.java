package com.domytask.catdog.services;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;

public interface UserService extends GenericService<UserEntity, Long>  {
    UserEntity taskReservation(TaskEntity taskEntity, UserEntity userEntity) throws NotFoundEntityException, NotAuthorizeActionException;

    UserEntity login(String email, String password) throws NotFoundEntityException;
}
