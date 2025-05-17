package spring.practice.elmenus_lite;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// Removed @EnableJpaAuditing to avoid requiring an auditorProvider bean.
// Auditing is not currently used in this project (e.g., @CreatedBy/@LastModifiedBy).
// Re-enable if auditing features are introduced and a proper AuditorAware implementation is provided.

//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class ElmenusLiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElmenusLiteApplication.class, args);
	}
	@PostConstruct
	public void method() {

	}
}
