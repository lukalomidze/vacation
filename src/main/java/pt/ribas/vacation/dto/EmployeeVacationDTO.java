package pt.ribas.vacation.dto;

import java.sql.Timestamp;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pt.ribas.vacation.enums.Status;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeVacationDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private Status status = Status.PENDING;

    private Timestamp statusChange;
}
