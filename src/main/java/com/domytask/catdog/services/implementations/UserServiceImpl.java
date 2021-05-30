package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.UserRepository;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotAuthorizeActionException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    WalletRepository walletRepo;

    String localisation = "userImpl";

    @Override
    public UserEntity save(UserEntity userEntity) throws InvalidEntityToPersistException {
        if(userEntity.getUserId() != null)
            throw new InvalidEntityToPersistException("Id invalide", "Un compte avec cet ID existe déjà", localisation+"save");
        else if (userRepo.findByEmail(userEntity.getEmail()).isPresent())
            throw new InvalidEntityToPersistException("Email invalide", "Cette adresse Email est déjà rattachée à un compte", localisation+"save");
        else{
            WalletEntity myWallet = new WalletEntity();
            userEntity.setWallet(myWallet);
            walletRepo.save(myWallet);
            return userRepo.save(userEntity);
        }
    }

    @Override
    public UserEntity update(UserEntity userEntity) throws NotFoundEntityException {
        if(!userRepo.findById(userEntity.getUserId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'objet n'existe pas", "");
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity taskReservation(TaskEntity taskEntity, UserEntity userEntity) throws NotFoundEntityException, NotAuthorizeActionException {
        if(!userRepo.findById(userEntity.getUserId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'objet n'existe pas", "");
        if(!taskEntity.getAvailable())
            throw new NotAuthorizeActionException("Action interdite", "Cette tache n'est plus disponible", "");
        if(taskEntity.getTaskStage() != TaskStageEnum.ONE && userEntity.getRole() != RoleEnum.MODERATOR)
            throw new NotAuthorizeActionException("Utilisateur invalide", "Cette tache est réservée aux Modérateurs", "");
        taskEntity.setAvailable(false);
        userEntity.getReservedTask().add(taskEntity);
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity getById(Long userId) throws NotFoundEntityException {
        if(!userRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Id invalide", "Aucun utilisateur", "");
        return userRepo.findById(userId).get();
    }

    @Override
    public List<UserEntity> all() throws NotFoundEntityException {
        return null;
    }

    @Override
    public void delete(Long userId) throws NotFoundEntityException {
        if(!userRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Aucun objet","Aucun objet n'a encore été créé dans la base de donnée", localisation+"delete");
        userRepo.deleteById(userId);
    }
}
