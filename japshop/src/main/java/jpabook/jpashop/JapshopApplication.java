package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule.Feature;

@SpringBootApplication
public class JapshopApplication {
	
	Hello hello = new Hello();

	public static void main(String[] args) {
		SpringApplication.run(JapshopApplication.class, args);
	}

	@Bean
	Hibernate5JakartaModule hibernate5Module() {
		Hibernate5JakartaModule hibernate5JakartaModule = new Hibernate5JakartaModule();
		//hibernate5JakartaModule.configure(Feature.FORCE_LAZY_LOADING, true);
		return hibernate5JakartaModule;
		
	}
}
