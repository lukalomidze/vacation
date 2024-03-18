package pt.ribas.vacation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ribas.vacation.entity.Employee;
import pt.ribas.vacation.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private Employee supervisor;
}
