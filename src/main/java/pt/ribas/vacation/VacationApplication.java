package pt.ribas.vacation;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VacationApplication {

	public static void main(String[] args) {
		SpringApplication.run(VacationApplication.class, args);
	}

	@Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
