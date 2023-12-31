package project_pet_backEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProjectPetBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectPetBackEndApplication.class, args);
	}

}
