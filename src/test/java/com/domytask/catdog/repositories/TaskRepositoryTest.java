package com.domytask.catdog.repositories;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 *DataJapTest supports rollback after running every test case
 */
@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepo;

    @BeforeEach
    public void dataSetup(){
        TaskEntity task = new TaskEntity(true, "url",0.00F, StatusEnum.TODO);
        task.setTaskStage(TaskStageEnum.ONE);
        taskRepo.save(task);
    }

    @Test
    void findAllByAvailableAndTaskStage() {
        List<TaskEntity> tasks = taskRepo.findAllByAvailableAndTaskStage(true, TaskStageEnum.ONE);
        assertEquals("url", tasks.get(0).getImageURL());
    }

}