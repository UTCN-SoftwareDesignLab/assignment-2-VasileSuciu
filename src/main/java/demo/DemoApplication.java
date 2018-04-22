package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("demo.model")
@EnableJpaRepositories({"demo.repository.book","demo.repository.sale","demo.repository.security","demo.repository.user"})
@ComponentScan({"demo.database","demo.model","demo.repository.book","demo.repository.sale","demo.repository.security","demo.repository.user",
		"demo.service.user","demo.service.book", "demo.service.report","demo.service.sale", "demo.controllers", "demo.service.googleBooks"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
