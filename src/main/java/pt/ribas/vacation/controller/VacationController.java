package pt.ribas.vacation.controller;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import pt.ribas.vacation.dto.AlterVacationDTO;
import pt.ribas.vacation.dto.AuthenticatedUserRoleDTO;
import pt.ribas.vacation.dto.BookVacationDTO;
import pt.ribas.vacation.dto.EmployeeDTO;
import pt.ribas.vacation.dto.RegisterEmployeeDTO;
import pt.ribas.vacation.dto.SupervisorEmployeeDTO;
import pt.ribas.vacation.service.VacationService;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:8080")
public class VacationController {
    @Autowired
    private VacationService service;

    @GetMapping("/current-user-role")
    public AuthenticatedUserRoleDTO getAuthenticatedUserRole() {
        return new AuthenticatedUserRoleDTO(
            SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()
        );
    }

    @GetMapping("/employee")
    public EmployeeDTO getEmployee() {
        return service.getEmployee(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @PostMapping("/book-vacation")
    public void bookVacation(
        @Valid @RequestBody BookVacationDTO vacationDTO
    ) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        try {
            service.bookVacation(vacationDTO, email);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                MessageFormat.format(
                    "Employee with email {0} has already requested" + 
                    " a vacation in the timespan {1} - {2}",
                    email,
                    vacationDTO.getStartDate(),
                    vacationDTO.getEndDate()
                )
            );
        }
    }

    @PostMapping("/register-employee")
    public void registerEmployee(
        @Valid @RequestBody RegisterEmployeeDTO employeeDTO
    ) {
        try {
            service.registerEmployee(employeeDTO);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                MessageFormat.format(
                    "Email {0} already in use",
                    employeeDTO.getEmail()
                )
            );
        }
    }

    @PutMapping("/alter-vacation-request")
    @PreAuthorize(
        "hasRole('ADMIN') or @securityService.isVacationSupervisor(#alterVacationDTO.vacationId)"
    )
    public void alterVacationRequest(
        @Valid @RequestBody AlterVacationDTO alterVacationDTO
    ) {
        service.alterVacationRequest(alterVacationDTO);
    }

    @GetMapping("/get-supervisor-employees")    
    public List<SupervisorEmployeeDTO> getSupervisorEmployees() {
        return service.getSupervisorEmployees(
            SecurityContextHolder.getContext().getAuthentication().getName()
        );
    }

    @GetMapping("/employee/all")
    public List<EmployeeDTO> getAllEmployees() {
        return service.getAllEmployees();
    }
    
}
