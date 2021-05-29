package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.TaskService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepo;

    String localization = "TaskImplementation";
    
    @Override
    public void save(TaskEntity taskEntity) throws InvalidEntityToPersistException {
        if (taskEntity.getTaskId() != null)
            throw new InvalidEntityToPersistException("Id Invalid", "Une adresse avec cet ID existe déjà", localization+" + save");
        taskEntity.setStatus(StatusEnum.TODO);
        taskEntity.setTaskStage(TaskStageEnum.TWO);
        taskRepo.save(taskEntity);
    }

    @Override
    public TaskEntity update(TaskEntity taskEntity) throws NotFoundEntityException {
        if (!taskRepo.findById(taskEntity.getTaskId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'object n'existe pas", "");
        return taskRepo.save(taskEntity);
    }

    @Override
    public TaskEntity taskFulfilment(TaskEntity taskEntity, UserEntity userEntity) throws NotFoundEntityException, NotAuthorizeActionException {
        /*if (taskEntity.getTaskDoers().get(0).getUserId() != userId )
            throw new NotAuthorizeActionException("Utilisateur non authorisé", "L'utilisateur n'est pas authorisé a effectué cette action", "");

         */
        System.out.println(userEntity.getWallet());
        if(!taskEntity.getStatus().equals(StatusEnum.TODO)){
            throw new NotAuthorizeActionException("Action interdite", "La tache a déjà été effectuée", "");
        }
        int getReview = getRandomNumber(0,3);
        if (getReview == 2){
            taskEntity.setAvailable(true);
            taskEntity.setStatus(StatusEnum.HOLD);
            if(userEntity.getWallet().getOnHold() == null){
                userEntity.getWallet().setOnHold(taskEntity.getReward());
            }else {
                userEntity.getWallet().setOnHold(userEntity.getWallet().getOnHold() + taskEntity.getReward());
            }
        } else {
            taskEntity.setStatus(StatusEnum.RELEASE);
            if(userEntity.getWallet().getAvailableBalance()==null){
                userEntity.getWallet().setAvailableBalance(taskEntity.getReward());
            }else {
                userEntity.getWallet().setAvailableBalance(userEntity.getWallet().getAvailableBalance() + taskEntity.getReward());
            }
        }
        taskEntity.setTaskStage(TaskStageEnum.TWO);
        taskEntity.setReview(false);
        return taskRepo.save(taskEntity);
    }

    public TaskEntity taskReview(TaskEntity taskEntity) {
        return null;
    }

    @Override
    public TaskEntity getById(Long userId) throws NotFoundEntityException {
        if(!taskRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Id invalide", "La tache n'existe pas", "");
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

    /**
     * Return a random number in a range between min and max value
     * @param min
     * @param max
     * @return
     */
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
