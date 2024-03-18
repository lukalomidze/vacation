package pt.ribas.vacation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookVacationDTO {
    @NotNull(message = "startDate must be provided in request body")
    private LocalDate startDate;

    @NotNull(message = "endDate must be provided in request body")
    private LocalDate endDate;

    @NotNull(message = "employeeId must be provided in request body")
    private Long employeeId;
}
