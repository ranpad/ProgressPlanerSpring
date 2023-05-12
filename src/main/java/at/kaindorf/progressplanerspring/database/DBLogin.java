package at.kaindorf.progressplanerspring.database;

import at.kaindorf.progressplanerspring.pojos.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class DBLogin {
    private static JdbcTemplate jdbcTemplate;

    public DBLogin(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void checkUser(String email, String password) {
        String sqlString = "SELECT * FROM customer WHERE email = ?";
        List<Customer> customers = jdbcTemplate.query(sqlString, new Object[]{email}, new BeanPropertyRowMapper<>(Customer.class));
        if (customers.isEmpty()) throw new RuntimeException("User doesn't exist");
        for (Customer c : customers) {
            if (!c.getSecret().equals(password)) {
                throw new RuntimeException("Wrong password");
            }
        }
    }
}
