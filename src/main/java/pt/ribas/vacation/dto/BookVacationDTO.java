package pt.ribas.vacation.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVacationDTO {
    @FutureOrPresent
    @NotNull(message = "startDate must be provided in request body")
    private LocalDate startDate;

    @FutureOrPresent
    @NotNull(message = "endDate must be provided in request body")
    private LocalDate endDate;
}
