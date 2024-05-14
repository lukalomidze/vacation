package pt.ribas.vacation.service;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pt.ribas.vacation.entity.Employee;
import pt.ribas.vacation.entity.Vacation;
import pt.ribas.vacation.repository.EmployeeRepository;
import pt.ribas.vacation.repository.VacationRepository;

@Service
public class SecurityService {
    @Autowired
    private VacationRepository vacationRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean isVacationSupervisor(Long vacationId) {
        Vacation vacation = vacationRepository
            .findById(vacationId)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                MessageFormat.format("Vacation with id {0} not found", vacationId)
            )
        );

        Employee supervisor = employeeRepository
            .findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
            )
        .get();

        Long vacationSupervisorId = vacation.getEmployee().getSupervisor().getId();

        return vacationSupervisorId.equals(supervisor.getId());
    }
}
