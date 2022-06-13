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
}