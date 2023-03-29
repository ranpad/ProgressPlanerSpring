package at.kaindorf.progressplanerspring.database;

import at.kaindorf.progressplanerspring.ProgressPlanerSpringApplication;
import at.kaindorf.progressplanerspring.pojos.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DB_TestImpl {
    private static JdbcTemplate jdbcTemplate;

    public DB_TestImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void testConnection(){
        String sqlString = "SELECT * FROM customer WHERE secret = 'saas'";
        List<Customer> customers = jdbcTemplate.query(sqlString,new BeanPropertyRowMapper<>(Customer.class));
        for (Customer customer: customers){
            System.out.println(customer);
        }
    }

}
