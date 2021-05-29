package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepo;

    @Autowired
    WalletRepository walletRepo;

    String localization = "TaskImplementation";
    
    @Override
    public void save(TaskEntity taskEntity) throws InvalidEntityToPersistException {
        if (taskEntity.getTaskId() != null)
            throw new InvalidEntityToPersistException("Id Invalid", "Une adresse avec cet ID existe déjà", localization+" + save");
        taskEntity.setStatus(StatusEnum.TODO);
        taskRepo.save(taskEntity);
    }

    @Override
    public TaskEntity update(TaskEntity taskEntity) throws NotFoundEntityException {
        if (!taskRepo.findById(taskEntity.getTaskId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'object n'existe pas", "");
        return taskRepo.save(taskEntity);
    }

    public TaskEntity taskFulfilment(TaskEntity taskEntity) throws  NotFoundEntityException {
        if (!taskRepo.findById(taskEntity.getTaskId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'object n'existe pas", "");
        int getReview = getRandomNumber(1,3);
        UserEntity doer = taskEntity.getTaskDoers().get(0);
        if (getReview == 3){
            taskEntity.setAvailable(true);
            taskEntity.setReview(true);
            taskEntity.setStatus(StatusEnum.HOLD);
            taskEntity.setTaskStage(TaskStageEnum.TWO);
            doer.getWallet().setOnHold(taskEntity.getReward());
        } else {
            taskEntity.setStatus(StatusEnum.RELEASE);
            doer.getWallet().setAvailableBalance(taskEntity.getReward());
        }
        return taskRepo.save(taskEntity);
    }

    public TaskEntity taskReview(TaskEntity taskEntity) {
        return null;
    }

    @Override
    public TaskEntity getById(Long userId) throws NotFoundEntityException {
        if(!taskRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Id invalide", "L'objet n'existe pas", "");
        return taskRepo.findById(userId).get();
    }

    @Override
    public List<TaskEntity> all() throws NotFoundEntityException {
        return taskRepo.findAll();
    }

    @Override
    public void delete(Long userId) throws NotFoundEntityException {
        if(!taskRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Id invalide", "L'objet n'existe pas", "");
        taskRepo.deleteById(userId);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
