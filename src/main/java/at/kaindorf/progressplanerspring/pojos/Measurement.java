package at.kaindorf.progressplanerspring.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Measurement {
    private int userId;
    private LocalDate validFrom;
    private double arm;
    private double waist;
    private double chest;
    private double leg;
    private double shoulder;
    private double calf;

    public List<Object> getSelectedFields(String column) {
        List<Object> selectedFields = new ArrayList<>();
        selectedFields.add(userId);
        selectedFields.add(validFrom);
        switch (column) {
            case "arm":
                selectedFields.add(arm);
                break;
            case "waist":
                selectedFields.add(waist);
                break;
            case "chest":
                selectedFields.add(chest);
                break;
            case "leg":
                selectedFields.add(leg);
                break;
            case "shoulder":
                selectedFields.add(shoulder);
                break;
            case "calf":
                selectedFields.add(calf);
                break;
            default:
                throw new IllegalArgumentException("Invalid column name: " + column);
        }
        return selectedFields;
    }
    public void setSelectedColumn(String column, double value) {
        switch (column) {
            case "arm":
                arm = value;
                break;
            case "waist":
                waist = value;
                break;
            case "chest":
                chest = value;
                break;
            case "leg":
                leg = value;
                break;
            case "shoulder":
                shoulder = value;
                break;
            case "calf":
                calf = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid column name: " + column);
        }
    }
}
