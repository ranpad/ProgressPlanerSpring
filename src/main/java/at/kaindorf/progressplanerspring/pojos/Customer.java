package at.kaindorf.progressplanerspring.pojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private int userId;
    private String username;
    private String email;
    private String secret;
    private LocalDate birthDate;
    private LocalDate createDate;
    private int profilePicture;
}
