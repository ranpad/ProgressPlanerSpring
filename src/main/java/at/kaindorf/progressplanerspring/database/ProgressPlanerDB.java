package at.kaindorf.progressplanerspring.database;

import at.kaindorf.progressplanerspring.pojos.Calorie;
import at.kaindorf.progressplanerspring.pojos.Customer;
import at.kaindorf.progressplanerspring.pojos.Measurement;
import at.kaindorf.progressplanerspring.pojos.Weight;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

//    public void createUser(Customer c) {
//        LocalDate createDate = LocalDate.now();
//        Period age = Period.between(c.getBirthDate(),createDate);
//        if (age.getYears()<18) throw new RuntimeException("User is not 18 yet");
//        String sqlString = "INSERT INTO customer (username, email, secret, birthdate, createdate, profilepicture)  VALUES (?,?,?,?,?,?,?)";
//        jdbcTemplate.update(sqlString, c.getUsername(), c.getEmail(), c.getSecret(), c.getBirthDate(), createDate, 1);
//    }


    public List<Weight> getWeightList(String email){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM weighthistory WHERE userid = ? ORDER BY validfrom";
        return jdbcTemplate.query(sqlString, new Object[]{userId}, new BeanPropertyRowMapper<>(Weight.class));
    }
    public List<Calorie> getCalorieList(String email){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM caloriehistory WHERE userid = ? AND validfrom >= (now() - INTERVAL '7' DAY) ORDER BY validfrom";
        return jdbcTemplate.query(sqlString, new Object[]{userId}, new BeanPropertyRowMapper<>(Calorie.class));
    }
    public List<Measurement> getMeasurementList(String email, String column){
        int userId = getUserId(email);
        String sqlString = "SELECT * FROM measurementhistory WHERE userid = ? ORDER BY validfrom";
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

    public void addWeight(Weight weight) {
        LocalDate date= weight.getValidFrom().plusDays(1);
        System.out.println(date);
        String sqlString = "SELECT COUNT(*) FROM weighthistory WHERE userid = ? AND validfrom = ?";
        int count = jdbcTemplate.queryForObject(sqlString, new Object[]{weight.getUserId(), date}, Integer.class);
        if (count > 0) {
            sqlString = "UPDATE weighthistory SET weight = ? WHERE userid = ? AND validfrom = ?";
            jdbcTemplate.update(sqlString, weight.getWeight(), weight.getUserId(), date);
        } else {
            sqlString = "INSERT INTO weighthistory (userid, weight, validfrom) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlString, weight.getUserId(), weight.getWeight(), date);
        }
    }
    public void addCalorie(Calorie calorie) {
        LocalDate date= calorie.getValidFrom().plusDays(1);
        String sqlString = "SELECT COUNT(*) FROM caloriehistory WHERE userid = ? AND validfrom = ?";
        int count = jdbcTemplate.queryForObject(sqlString, new Object[]{calorie.getUserId(), date}, Integer.class);
        if (count > 0) {
            sqlString = "UPDATE caloriehistory SET calorie = ? WHERE userid = ? AND validfrom = ?";
            jdbcTemplate.update(sqlString, calorie.getCalories(), calorie.getUserId(), date);
        } else {
            sqlString = "INSERT INTO caloriehistory (userid, calorie, validfrom) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlString, calorie.getCalories(), calorie.getUserId(), date);
        }
    }
    public void addMeasurement(Measurement measurement){
        LocalDate date= measurement.getValidFrom().plusDays(1);
        String sqlString = "SELECT COUNT(*) FROM measurementhistory WHERE userid = ? AND validfrom = ?";
        int count = jdbcTemplate.queryForObject(sqlString, new Object[]{measurement.getUserId(), date}, Integer.class);

        if (count > 0) {
            sqlString = "UPDATE measurementhistory SET";
            if (measurement.getArm() != 0) sqlString +=  " arm = " + measurement.getArm();
            if (measurement.getWaist() != 0) sqlString +=  " waist = " + measurement.getWaist();
            if (measurement.getChest() != 0) sqlString +=  " chest = " + measurement.getChest();
            if (measurement.getLeg() != 0) sqlString +=  " leg = " + measurement.getLeg();
            if (measurement.getShoulder() != 0) sqlString +=  " shoulder = " + measurement.getShoulder();
            if (measurement.getCalf() != 0) sqlString +=  " calf = " + measurement.getCalf();
            sqlString +=  " WHERE userid = ? AND validfrom = ?";
            jdbcTemplate.update(sqlString, measurement.getUserId(), date);
        } else {
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO measurementhistory (userid, validfrom");
            List<Object> params = new ArrayList<>();
            params.add(measurement.getUserId());
            params.add(date);
            if (measurement.getArm() != 0) {
                sqlBuilder.append(", arm");
                params.add(measurement.getArm());
            }
            if (measurement.getWaist() != 0) {
                sqlBuilder.append(", waist");
                params.add(measurement.getWaist());
            }
            if (measurement.getChest() != 0) {
                sqlBuilder.append(", chest");
                params.add(measurement.getChest());
            }
            if (measurement.getLeg() != 0) {
                sqlBuilder.append(", leg");
                params.add(measurement.getLeg());
            }
            if (measurement.getShoulder() != 0) {
                sqlBuilder.append(", shoulder");
                params.add(measurement.getShoulder());
            }
            if (measurement.getCalf() != 0) {
                sqlBuilder.append(", calf");
                params.add(measurement.getCalf());
            }
            sqlBuilder.append(") VALUES (?");
            for (int i = 1; i < params.size(); i++) {
                sqlBuilder.append(", ?");
            }
            sqlBuilder.append(")");
            sqlString = sqlBuilder.toString();
            jdbcTemplate.update(sqlString, params.toArray());
        }

    }
}
