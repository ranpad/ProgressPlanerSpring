package at.kaindorf.progressplanerspring.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Calorie {
    private int userId;
    private LocalDate validFrom;
    private int calories;

}
