package pt.ribas.vacation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ribas.vacation.enums.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeDTO {

    @NotBlank(message = "firstName must be provided in request body")
    private String firstName;

    @NotBlank(message = "lastName must be provided in request body")
    private String lastName;

    @NotBlank(message = "email must be provided in request body")
    private String email;
    
    @NotBlank(message = "password must be provided in request body")
    private String password;

    @NotNull(message = "gender must be provided in request body")
    private Gender gender;

    @NotNull(message = "supervisorId must be provided in request body")
    private RegisterEmployeeSupervisorDTO supervisor;
}
