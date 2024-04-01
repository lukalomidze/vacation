package pt.ribas.vacation.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ribas.vacation.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Gender gender;

    private SupervisorDTO supervisor;

    private List<EmployeeVacationDTO> vacations;
}
