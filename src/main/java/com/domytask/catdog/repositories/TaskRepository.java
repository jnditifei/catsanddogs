package com.domytask.catdog.repositories;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Optional<TaskEntity> findByAvailable(boolean available);

    Optional<TaskEntity> findByStatus(StatusEnum status);
}
