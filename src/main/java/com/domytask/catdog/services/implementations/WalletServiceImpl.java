package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.WalletService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    @Autowired
    WalletRepository walletRepo;

    @Override
    public void save(WalletEntity walletEntity) throws InvalidEntityToPersistException {
    }

    @Override
    public WalletEntity update(WalletEntity walletEntity) throws NotFoundEntityException {
        return null;
    }

    @Override
    public WalletEntity getById(Long userId) throws NotFoundEntityException {
        return null;
    }

    @Override
    public List<WalletEntity> all() throws NotFoundEntityException {
        return null;
    }

    @Override
    public void delete(Long userId) throws NotFoundEntityException {

    }
}
