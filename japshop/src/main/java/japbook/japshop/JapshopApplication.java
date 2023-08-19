package japbook.japshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JapshopApplication {
	
	Hello hello = new Hello();
	
	public static void main(String[] args) {
		SpringApplication.run(JapshopApplication.class, args);
	}

}
