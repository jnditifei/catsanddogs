package com.domytask.catdog.services.implementations;

import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.repositories.WalletRepository;
import com.domytask.catdog.services.WalletService;
import com.domytask.catdog.services.exceptions.InvalidEntityToPersistException;
import com.domytask.catdog.services.exceptions.NotFoundEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepo;
    @Override
    public WalletEntity save(WalletEntity walletEntity) throws InvalidEntityToPersistException {
        return  null;
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
