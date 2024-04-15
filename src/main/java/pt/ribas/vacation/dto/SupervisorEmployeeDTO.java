package pt.ribas.vacation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ribas.vacation.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupervisorEmployeeDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private List<EmployeeVacationDTO> vacations;
}
