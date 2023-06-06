package at.kaindorf.progressplanerspring.web;

import at.kaindorf.progressplanerspring.database.ProgressPlanerDB;
import at.kaindorf.progressplanerspring.pojos.Calorie;
import at.kaindorf.progressplanerspring.pojos.Measurement;
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
        ProgressPlanerDB progressPlanerDb = new ProgressPlanerDB(jdbcTemplate);
        List<Weight> weightList = progressPlanerDb.getWeightList(email);
        return new ResponseEntity<>(weightList, HttpStatus.OK).getBody();
    }
    @GetMapping("/calories")
    public List<Calorie> getUserCalories(@RequestHeader("user-mail") String email) {
        ProgressPlanerDB progressPlanerDb = new ProgressPlanerDB(jdbcTemplate);
        List<Calorie> calorieList = progressPlanerDb.getCalorieList(email);
        return new ResponseEntity<>(calorieList, HttpStatus.OK).getBody();
    }
    @GetMapping("/measurements")
    public List<Measurement> getUserMeasurements(@RequestHeader("user-mail") String email, @RequestHeader("column") String column) {
        ProgressPlanerDB progressPlanerDB = new ProgressPlanerDB(jdbcTemplate);
        List<Measurement> measurementList = progressPlanerDB.getMeasurementList(email,column);
        return new ResponseEntity<>(measurementList, HttpStatus.OK).getBody();
    }

}
