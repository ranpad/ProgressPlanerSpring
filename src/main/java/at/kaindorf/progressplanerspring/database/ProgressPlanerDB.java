package at.kaindorf.progressplanerspring.database;

import at.kaindorf.progressplanerspring.pojos.Calorie;
import at.kaindorf.progressplanerspring.pojos.Customer;
import at.kaindorf.progressplanerspring.pojos.Measurement;
import at.kaindorf.progressplanerspring.pojos.Weight;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProgressPlanerDB {
    private static JdbcTemplate jdbcTemplate;

    public ProgressPlanerDB(JdbcTemplate jdbcTemplate) {
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
    public int getUserId(String email) {
        String sqlString = "SELECT userid FROM customer WHERE email = ?";
        List<Customer> customers = jdbcTemplate.query(sqlString, new Object[]{email}, new BeanPropertyRowMapper<>(Customer.class));
        if (customers.isEmpty()) throw new RuntimeException("User doesn't exist");
        return customers.stream().
                findFirst().
                get().
                getUserId();
    }
    public List<Weight> getWeightList(String email){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM weighthistory WHERE userid = ?";
        return jdbcTemplate.query(sqlString, new Object[]{userId}, new BeanPropertyRowMapper<>(Weight.class));
    }
    public List<Calorie> getCalorieList(String email){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM caloriehistory WHERE userid = ?";
        return jdbcTemplate.query(sqlString, new Object[]{userId}, new BeanPropertyRowMapper<>(Calorie.class));
    }
    public List<Measurement> getMeasurementList(String email, String column){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM measurementhistory WHERE userid = ?";
        List<Measurement> measurements = jdbcTemplate.query(sqlString, new Object[]{userId}, new BeanPropertyRowMapper<>(Measurement.class));
        List<Measurement> selectedMeasurements = new ArrayList<>();
        for (Measurement measurement : measurements) {
            Measurement selectedMeasurement = new Measurement();
            selectedMeasurement.setUserId(measurement.getUserId());
            selectedMeasurement.setValidFrom(measurement.getValidFrom());
            selectedMeasurement.setSelectedColumn(column, (Double)measurement.getSelectedFields(column).get(2));
            selectedMeasurements.add(selectedMeasurement);
        }
        return selectedMeasurements;
    }
}