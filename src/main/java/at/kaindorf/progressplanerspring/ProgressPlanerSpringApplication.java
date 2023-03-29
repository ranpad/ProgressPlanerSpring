package at.kaindorf.progressplanerspring;

import at.kaindorf.progressplanerspring.database.DB_TestImpl;
import at.kaindorf.progressplanerspring.pojos.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
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

}
