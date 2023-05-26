package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.database.DBLogin;
import at.kaindorf.progressplanerspring.pojos.Calorie;
import at.kaindorf.progressplanerspring.pojos.Weight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
@RestController
public class ChartController {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public ChartController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public ResponseEntity<Object> test(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/weight")
    public List<Weight> getUserWeight(@RequestHeader("user-mail") String email) {
        DBLogin dbLogin = new DBLogin(jdbcTemplate);
        List<Weight> weightList = dbLogin.getWeightList(email);
        return new ResponseEntity<>(weightList, HttpStatus.OK).getBody();
    }
    @GetMapping("/calories")
    public List<Calorie> getUserCalories(@RequestParam String email) {
        DBLogin dbLogin = new DBLogin(jdbcTemplate);
        List<Calorie> weightList = dbLogin.getCalorieList(email);
        return new ResponseEntity<>(weightList, HttpStatus.OK).getBody();
    }
//    @GetMapping("/measurements")
//    public List<Measurement> getUserMeasurements(@RequestParam String emajil) {
//        DBLogin dbLogin = new DBLogin(jdbcTemplate);
//        List<Weight> weightList = dbLogin.getWeightList(email);
//        return new ResponseEntity<>(weightList, HttpStatus.OK).getBody();
//    }

}
