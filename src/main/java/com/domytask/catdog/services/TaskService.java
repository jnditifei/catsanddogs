package com.domytask.catdog.services;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;

import java.util.List;

public interface TaskService extends GenericService<TaskEntity, Long> {
    TaskEntity taskFulfilment(TaskEntity taskEntity, UserEntity userEntity) throws NotFoundEntityException, NotAuthorizeActionException;

    List<TaskEntity> getAllTasksAvailableByStage(TaskStageEnum taskStage);

    TaskEntity taskReview(TaskEntity taskEntity, UserEntity userEntity) throws NotAuthorizeActionException;
}
