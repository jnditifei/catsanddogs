package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
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
    public TaskEntity save(TaskEntity taskEntity) throws InvalidEntityToPersistException {
        if (taskEntity.getTaskId() != null)
            throw new InvalidEntityToPersistException("Id Invalid", "Une adresse avec cet ID existe déjà", localization+" + save");
        taskEntity.setStatus(StatusEnum.TODO);
        taskEntity.setTaskStage(TaskStageEnum.ONE);
        return taskRepo.save(taskEntity);
    }
    @Override
    public TaskEntity update(TaskEntity taskEntity) throws NotFoundEntityException {
        if (!taskRepo.findById(taskEntity.getTaskId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'object n'existe pas", "");
        return taskRepo.save(taskEntity);
    }
    @Override
    public TaskEntity taskFulfilment(TaskEntity taskEntity, UserEntity userEntity) throws NotAuthorizeActionException {
        if (taskEntity.getTaskDoers().get(0) != userEntity )
            throw new NotAuthorizeActionException("Utilisateur non authorisé", "L'utilisateur n'est pas authorisé a effectué cette action", "");

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
    @Override
    public List<TaskEntity> getAllTasksAvailableByStage(TaskStageEnum taskStage) {
        return taskRepo.findAllByAvailableAndTaskStage(true, taskStage);
    }
    @Override
    public TaskEntity taskReview(TaskEntity taskEntity, UserEntity userEntity) throws NotAuthorizeActionException {
        if (taskEntity.getTaskDoers().get(1) != userEntity )
            throw new NotAuthorizeActionException("Utilisateur non authorisé", "L'utilisateur n'est pas authorisé a effectué cette action", "");
        Float walletOnHold = taskEntity.getTaskDoers().get(0).getWallet().getOnHold();
        Float walletAvailable = taskEntity.getTaskDoers().get(0).getWallet().getAvailableBalance();
        if(taskEntity.getStatus()==StatusEnum.FAIL){
            taskEntity.getTaskDoers().get(0).getWallet().setOnHold(walletOnHold - taskEntity.getReward());
        }else {
            taskEntity.getTaskDoers().get(0).getWallet().setOnHold(walletOnHold - taskEntity.getReward());
            if(walletAvailable == null) {
                taskEntity.getTaskDoers().get(0).getWallet().setAvailableBalance(taskEntity.getReward());
            }else {
                taskEntity.getTaskDoers().get(0).getWallet().setAvailableBalance(walletAvailable + taskEntity.getReward());
            }
        }
        taskEntity.setReview(true);
        taskEntity.setTaskStage(TaskStageEnum.THREE);
        return taskRepo.save(taskEntity);
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

    public List<TaskEntity> getAllAvailableTasksByStage(TaskStageEnum taskStage){
        return taskRepo.findAllByAvailableAndTaskStage(true, taskStage);
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
