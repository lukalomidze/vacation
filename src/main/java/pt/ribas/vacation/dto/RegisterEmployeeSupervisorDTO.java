package pt.ribas.vacation.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterEmployeeSupervisorDTO {
    @NotNull(message = "Id must be provided")
    private Long id;
}
