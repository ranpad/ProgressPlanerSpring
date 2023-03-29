package at.kaindorf.progressplanerspring.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
    private int userId;
    private LocalDate validFrom;
    private double arm;
    private double waist;
    private double chest;
    private double leg;
    private double shoulder;
    private double calf;
}
