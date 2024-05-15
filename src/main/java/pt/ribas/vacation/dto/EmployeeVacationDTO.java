package pt.ribas.vacation.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ribas.vacation.enums.Status;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeVacationDTO {
    private Long id;
    
    private LocalDate startDate;

    private LocalDate endDate;

    private Status status = Status.PENDING;

    private Timestamp statusChange;
}
