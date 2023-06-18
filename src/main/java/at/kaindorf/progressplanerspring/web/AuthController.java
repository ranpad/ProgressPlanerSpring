package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.database.ProgressPlanerDB;
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
        ProgressPlanerDB progressPlanerDb = new ProgressPlanerDB(jdbcTemplate);
        try {
            progressPlanerDb.checkUser(loginForm.getEmail(), loginForm.getPassword());
        }catch (RuntimeException e){
            return ResponseEntity.status(402).build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer customer){
        ProgressPlanerDB progressPlanerDB = new ProgressPlanerDB(jdbcTemplate);
        try {
            progressPlanerDB.checkUser(customer.getEmail(), customer.getSecret());
        }catch (RuntimeException e){
            return ResponseEntity.status(402).build();
        }
        return ResponseEntity.ok().build();
    }

}