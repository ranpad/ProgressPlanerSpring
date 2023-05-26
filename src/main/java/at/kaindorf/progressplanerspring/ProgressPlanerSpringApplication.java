package at.kaindorf.progressplanerspring;

import at.kaindorf.progressplanerspring.database.DB_TestImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProgressPlanerSpringApplication {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public ProgressPlanerSpringApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProgressPlanerSpringApplication.class, args);
        System.out.println("!!!!!!!");
        DB_TestImpl dbTest = new DB_TestImpl(jdbcTemplate);
        dbTest.testConnection();
    }

    @GetMapping("/mast")
    public String sayHello() {
        return "Hallo /mast";
    }
}
