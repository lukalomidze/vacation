package pt.ribas.vacation.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeSupervisorDTO {
    @NotBlank(message = "Email must be provided")
    private String email;
}
