package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.UserRepository;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.UserService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
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
    public void save(UserEntity userEntity) throws InvalidEntityToPersistException {
        if(userEntity.getUserId() != null)
            throw new InvalidEntityToPersistException("Id invalide", "Un compte avec cet ID existe déjà", localisation+"save");
        else if (userRepo.findByEmail(userEntity.getEmail()).isPresent())
            throw new InvalidEntityToPersistException("Email invalide", "Cette adresse Email est déjà rattachée à un compte", localisation+"save");
        else{
            WalletEntity myWallet = new WalletEntity();
            userEntity.setWallet(myWallet);
            walletRepo.save(myWallet);
            userRepo.save(userEntity);
        }
    }

    @Override
    public UserEntity update(UserEntity userEntity) throws NotFoundEntityException {
        if(!userRepo.findById(userEntity.getUserId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'objet n'existe pas", "");
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity taskReservation(TaskEntity taskEntity, UserEntity userEntity) throws  NotFoundEntityException {
        if(!userRepo.findById(userEntity.getUserId()).isPresent())
            throw new NotFoundEntityException("Id Invalide", "L'objet n'existe pas", "");
        System.out.println(taskEntity.getImageURL());
        userEntity.getReservedTask().add(taskEntity);
        return userRepo.save(userEntity);
    }

    @Override
    public UserEntity getById(Long userId) throws NotFoundEntityException {
        if(!userRepo.findById(userId).isPresent())
            throw new NotFoundEntityException("Id invalide", "L'objet n'existe pas", "");
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
        UserEntity deleteBuyer = userRepo.findById(userId).get();
        userRepo.delete(deleteBuyer);
    }
}
