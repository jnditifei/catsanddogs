package com.domytask.catdog.repositories;

import com.domytask.catdog.entities.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
}
