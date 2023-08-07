package project_pet_backEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProjectPetBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectPetBackEndApplication.class, args);
	}

}
