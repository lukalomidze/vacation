package pt.ribas.vacation.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterEmployeeSupervisorDTO {
    @NotNull(message = "Email must be provided")
    private String email;
}
