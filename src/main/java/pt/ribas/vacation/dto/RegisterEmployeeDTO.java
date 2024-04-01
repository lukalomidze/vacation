package pt.ribas.vacation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pt.ribas.vacation.enums.Gender;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterEmployeeDTO {

    @NotBlank(message = "firstName must be provided in request body")
    private String firstName;

    @NotBlank(message = "lastName must be provided in request body")
    private String lastName;

    @NotBlank(message = "email must be provided in request body")
    private String email;

    @NotNull(message = "gender must be provided in request body")
    private Gender gender;

    @NotNull(message = "supervisorId must be provided in request body")
    private RegisterEmployeeSupervisorDTO supervisor;
}
