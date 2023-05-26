package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.database.DBLogin;
import at.kaindorf.progressplanerspring.pojos.Customer;
import at.kaindorf.progressplanerspring.pojos.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;



@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class AuthController {

    private JdbcTemplate jdbcTemplate;
    @Autowired
    public AuthController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody LoginForm loginForm){
        DBLogin dbLogin = new DBLogin(jdbcTemplate);
        try {
            dbLogin.checkUser(loginForm.getEmail(), loginForm.getPassword());
        }catch (RuntimeException e){
            return ResponseEntity.status(402).build();
        }
        return ResponseEntity.ok().build();
    }

}