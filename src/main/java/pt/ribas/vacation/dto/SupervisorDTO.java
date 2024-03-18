package pt.ribas.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pt.ribas.vacation.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupervisorDTO {
    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;
}
