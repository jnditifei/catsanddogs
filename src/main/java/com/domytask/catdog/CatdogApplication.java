package com.domytask.catdog;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.entities.WalletEntity;
import com.domytask.catdog.entities.enums.RoleEnum;
import com.domytask.catdog.entities.enums.StatusEnum;
import com.domytask.catdog.entities.enums.TaskStageEnum;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CatdogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatdogApplication.class, args);
	}

/*
	@Bean
	public CommandLineRunner mappingDemo(UserRepository userRepository, TaskRepository taskRepository)  {
		return args -> {
			TaskEntity task1 = new TaskEntity(true, "https://photos.lci.fr/images/1024/576/larry-the-cat-20h-tf1-f088a6-0@1x.jpeg", 125.00F, StatusEnum.TODO, TaskStageEnum.ONE);
			TaskEntity task2 = new TaskEntity(true, "https://bigcats.be/images/resized/750x-header-cat.jpg", 100.00F, StatusEnum.TODO, TaskStageEnum.ONE);
			TaskEntity task3 = new TaskEntity(true, "https://animo-boutik.com/6152-large_default/dog-copenhagen-harnais-confort-walk-pro.jpg", 100.00F, StatusEnum.TODO, TaskStageEnum.ONE);
			List<TaskEntity> tasks = new ArrayList<>();
			tasks.add(task1);
			tasks.add(task2);
			tasks.add(task3);
			taskRepository.saveAll(tasks);
			UserEntity user1 = new UserEntity("username", "username@test.com", "password", RoleEnum.ENTRY, new WalletEntity());
			userRepository.save(user1);
			UserEntity user2 = new UserEntity("modo", "modo@test.com", "password", RoleEnum.MODERATOR, new WalletEntity());
			userRepository.save(user2);
		};
	}

 */
}