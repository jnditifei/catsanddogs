package com.domytask.catdog;

import com.domytask.catdog.entities.TaskEntity;
import com.domytask.catdog.entities.UserEntity;
import com.domytask.catdog.repositories.TaskRepository;
import com.domytask.catdog.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatdogApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatdogApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner mappingDemo(UserRepository userRepository, TaskRepository taskRepository)  {
		return args -> {
			TaskEntity task = taskRepository.findById(1L).get();
			UserEntity user = userRepository.findById(1L).get();
			user.getReservedTask().add(task);
			userRepository.save(user);
		};
	}
	*/
}