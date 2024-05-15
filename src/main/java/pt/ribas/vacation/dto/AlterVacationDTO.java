package pt.ribas.vacation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ribas.vacation.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterVacationDTO {
    @NotNull(message = "vacationId must be provided in request body")
    private Long vacationId;

    @NotNull(message = "status must be provided in request body")
    private Status status;
}
